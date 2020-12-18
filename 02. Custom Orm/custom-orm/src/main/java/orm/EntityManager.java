package orm;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EntityManager<T> implements DbContext<T>{
    private final Connection connection;

    public EntityManager(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public boolean persist(T entity) throws IllegalAccessException, SQLException {
        if (!this.existsTable(entity.getClass().getAnnotation(Entity.class).name())) {
            doCreate(entity.getClass());
        }else {
            doAlter(entity.getClass()); // does nothing if same
        }

        Field primaryKeyField = this.getId(entity.getClass());
        primaryKeyField.setAccessible(true);

        Object value = primaryKeyField.get(entity);

        if ((int) value <= 0) {
            return doInsert(entity, primaryKeyField);
        }

        return doUpdate(entity, primaryKeyField);
    }

    public Iterable<T> find(Class<T> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return find(table, null);
    }

    public Iterable<T> find(Class<T> table, String where) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String query = "SELECT * FROM " + this.getTableName(table) + " WHERE 1" + (where != null ? " AND " + where : "");
        PreparedStatement stmt = this.connection.prepareStatement(query);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) return null;
        List<T> list = new ArrayList<>();

        do {
            Constructor<T> ctr = table.getConstructor();
            T entity = ctr.newInstance();

            this.fillEntity(rs, entity);
            list.add(entity);
        } while (rs.next());

        return list;
    }

    public T findFirst(Class<T> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return findFirst(table, null);
    }

    public T findFirst(Class<T> table, String where) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String query = "SELECT * FROM " + this.getTableName(table) + " WHERE 1" + (where != null ? " AND " + where : "") + " LIMIT 1";
        PreparedStatement stmt = this.connection.prepareStatement(query);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) return null;
        Constructor<T> ctr = table.getConstructor();
        T entity = ctr.newInstance();

        this.fillEntity(rs, entity);
        return entity;
    }

    @Override
    public int delete(Class<T> table, String where) throws SQLException {
        String query = "DELETE FROM " + this.getTableName(table) + " WHERE " + where;

        PreparedStatement stmt = this.connection.prepareStatement(query);
        return stmt.executeUpdate();
    }

    private void fillEntity(ResultSet rs, T entity) throws SQLException, IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            this.fillField(field, entity, rs, field.getAnnotation(Column.class).name());
            field.setAccessible(false);
        }
    }

    private void fillField(Field field, T entity, ResultSet rs, String fieldName) throws SQLException, IllegalAccessException {
        if (field.getType() == int.class || field.getType() == Integer.class) {
            field.set(entity, rs.getInt(fieldName));
        } else if (field.getType() == double.class || field.getType() == Double.class) {
            field.set(entity, rs.getDouble(fieldName));
        } else if (field.getType() == long.class || field.getType() == Long.class) {
            field.set(entity, rs.getLong(fieldName));
        } else if (field.getType() == float.class || field.getType() == Float.class) {
            field.set(entity, rs.getFloat(fieldName));
        } else if (field.getType() == String.class) {
            field.set(entity, rs.getString(fieldName));
        } else if (field.getType() == Date.class) {
            java.sql.Date sqlDate = rs.getDate(fieldName);

            field.set(entity, new Date(sqlDate.getTime()));
        }
        // TODO Add more types
    }

    private Field getId(Class<?> entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow( () ->
                        new UnsupportedOperationException("Entity does not have a primary key!")
                );
    }

    private boolean doInsert(T entity, Field primaryKeyField) throws SQLException, IllegalAccessException {
        String tableName = this.getTableName(entity.getClass());
        StringBuilder[] args = this.getTableNameAndValues(entity, QueryType.INSERT);
        StringBuilder columns = args[0];
        StringBuilder values = args[1];

        String insertionQuery = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";

        int rowsAffected = this.connection.prepareStatement(insertionQuery).executeUpdate();

        if (rowsAffected == 0) return false;

        String lastIdQuery = "SELECT LAST_INSERT_ID() AS id";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(lastIdQuery);

        rs.next();
        int lastId = rs.getInt("id");

        primaryKeyField.setAccessible(true);
        primaryKeyField.set(entity, lastId);
        primaryKeyField.setAccessible(false);

        return true;
    }

    private boolean doUpdate(T entity, Field primaryKeyField) throws IllegalAccessException, SQLException {
        String tableName = this.getTableName(entity.getClass());
        StringBuilder values = this.getTableNameAndValues(entity, QueryType.UPDATE)[0];
        String idNameDb = primaryKeyField.getAnnotation(Column.class).name();
        int idValue = (int)primaryKeyField.get(entity);

        String updateQuery = "UPDATE " + tableName + " SET " + values + " WHERE " + idNameDb + " = " + idValue;
        int rowsAffected = this.connection.prepareStatement(updateQuery).executeUpdate();

        return rowsAffected != 0;
    }

    private void doCreate(Class<?> entity) throws SQLException {
        String query = "CREATE TABLE " + this.getTableName(entity) + " (\n";
        StringBuilder columnDefinition = new StringBuilder();

        Field[] fields = entity.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            columnDefinition.append(field.getAnnotation(Column.class).name());
            columnDefinition.append(" ");
            columnDefinition.append(this.getSQLType(field));

            if (field.isAnnotationPresent(Id.class)) {
                columnDefinition.append(" AUTO_INCREMENT PRIMARY KEY");
            }

            columnDefinition.append(", \n");
            field.setAccessible(false);
        }

        columnDefinition.delete(columnDefinition.length() - 3, columnDefinition.length() - 1);
        columnDefinition.append(")");
        query += columnDefinition.toString();

        this.connection.prepareStatement(query).execute();
    }

    private void doAlter(Class<?> entity) throws SQLException {
        StringBuilder columnDefinition = new StringBuilder();

        Field[] fields = entity.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(Column.class)) {
                String columnName = field.getAnnotation(Column.class).name();
                if (!this.existsColumn(columnName)) {
                    columnDefinition.append(columnName);
                    columnDefinition.append(" ");
                    columnDefinition.append(this.getSQLType(field));
                    columnDefinition.append(",\n");
                }
            }

            field.setAccessible(false);
        }

        if (columnDefinition.length() != 0) {
            columnDefinition.delete(columnDefinition.length() - 2, columnDefinition.length() - 1);
            String query = "ALTER TABLE " + this.getTableName(entity) + "\n ADD(" + columnDefinition.toString() + ")";

            this.connection.prepareStatement(query).execute();
        }
    }

    private String getTableName(Class<?> entity) {
        return entity.getAnnotation(Entity.class).name();
    }

    private StringBuilder[] getTableNameAndValues(T entity, QueryType queryType) throws IllegalAccessException {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                if (!field.isAnnotationPresent(Id.class)) {

                    switch (queryType) {
                        case INSERT:
                            columns.append('`');
                            columns.append(field.getAnnotation(Column.class).name());
                            columns.append("`, ");
                            break;
                        case UPDATE:
                            values.append('`');
                            values.append(field.getAnnotation(Column.class).name());
                            values.append("` = ");
                            break;
                    }

                    Object value = field.get(entity);

                    if (value instanceof Date) {
                        values.append("'");
                        values.append(new SimpleDateFormat("yyyy-MM-dd").format(value));
                        values.append("', ");
                    } else if (value instanceof Number) {
                        values.append(value);
                        values.append(", ");
                    } else {
                        values.append("'");
                        values.append(value);
                        values.append("', ");
                    }
                }
            }

            field.setAccessible(false);
        }

        StringBuilder[] sbArr = new StringBuilder[2];

        if (queryType.equals(QueryType.INSERT)) {
            columns.delete(columns.length() - 2, columns.length());
            values.delete(values.length() - 2, values.length());
            sbArr[0] = columns;
            sbArr[1] = values;
        }else {
            values.delete(values.length() - 2, values.length());
            sbArr[0] = values;
            sbArr[1] = null;
        }

        return sbArr;
    }

    private String getSQLType(Field field) {
        Class<?> type = field.getType();

        if (type == Integer.class || type == int.class) {
            return "INT";
        } else if (type == String.class) {
            return "VARCHAR(255)";
        }else if (type == Date.class) {
            return "DATE";
        }
        //TODO add more
        return null;
    }

    private boolean existsTable(String tableName) throws SQLException {
        String query = "SHOW tables";
        PreparedStatement stmt = this.connection.prepareStatement(query);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            if (rs.getString(1).equals(tableName)) return true;
        }
        return false;
    }

    private boolean existsColumn(String columnName) throws SQLException {
        String query = "SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE COLUMN_NAME = ? LIMIT 1";
        PreparedStatement stmt = this.connection.prepareStatement(query);

        stmt.setString(1, columnName);
        ResultSet rs = stmt.executeQuery();

        return rs.next();
    }
}

