package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class Connector {
    private static final String USER_PROPERTY = "user";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";

    private static Connection connection;

    public static void createConnection(String user, String password, String dbName) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty(USER_PROPERTY, user);
        properties.setProperty(PASSWORD_PROPERTY, password);

        connection = DriverManager.getConnection(CONNECTION_STRING + dbName, properties);
    }

    public static Connection getConnection() {
        return connection;
    }
}
