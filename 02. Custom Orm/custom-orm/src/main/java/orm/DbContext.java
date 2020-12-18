package orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DbContext<T> {
    //it will insert or update entity depending if it is attached to the context
    boolean persist(T entity) throws IllegalAccessException, SQLException;

    //returns collection of all entity objects of type T
    Iterable<T> find(Class<T> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    //returns collection of all entity objects of type T matching the criteria given in “where”
    Iterable<T> find(Class<T> table, String where) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    //returns the first entity object of type T
    T findFirst(Class<T> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    //returns the first entity object of type T matching the criteria given in “where”
    T findFirst(Class<T> table, String where) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    int delete(Class<T> table, String where) throws SQLException;
}
