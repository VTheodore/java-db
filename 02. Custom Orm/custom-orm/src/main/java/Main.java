import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Connector.createConnection("root", "theodore123", "orm_db");
        EntityManager<User> em = new EntityManager<>(Connector.getConnection());

        System.out.println(em.delete(User.class, "id = 1"));
    }
}
