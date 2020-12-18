import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class Main {
    private static final String MYSQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String SOFT_UNI_DB = "soft_uni";
    private static final String MINIONS_DB = "minions_db";
    private static final String EMPTY_STRING = "";
    private static final String REGEX_SPLIT_SPACE = "\\s+";
    private static final String DEFAULT_EVILNESS_FACTOR = "evil";
    private static final double DEFAULT_SALARY = 20000;
    private static Connection connection;
    private static String query;
    private static BufferedReader reader;

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        Class.forName(MYSQL_DRIVER_NAME); //Load Driver

        reader = new BufferedReader(new InputStreamReader(System.in)); //Setting up reader

        Properties properties = new Properties(); //Credentials
        properties.setProperty("user", "root");
        properties.setProperty("password", "theodore123");

        connection = DriverManager.getConnection(CONNECTION_STRING + MINIONS_DB, properties); //Set connection
        //LAB 01. Get employees with salary greater than given number
        //getEmployeesWithSalaryGreaterThan();

        /*-----------Those bellow are from EXC-----------*/

        //01. Get Villains' Names with more than 15 minions
        //getVillainsNamesHavingMoreThan15Minions();

        //02. Get Villain with his minions
        //getVillainAndHisMinions();

        //03. Add minion
        //addMinion();

        //04. Change town names casing
        //updateTownNamesToUppercase();

        //05. Remove Villain
        //removeVillain();

        //06. Print Minions' names in specific order (first, last, first + 1, last - 1, ...)
        //printMinionsNames();

        //07. Increase Minions Age
        //increaseMinionsAge();

        //08. Increase Age with stored procedure
        //increaseAgeWithProcedure();
        connection.close();
    }

    private static void getEmployeesWithSalaryGreaterThan() throws IOException, SQLException {
        System.out.print("Enter salary (default 20000): ");
        String salaryAsString = reader.readLine();
        double salary = salaryAsString.equals(EMPTY_STRING) ? DEFAULT_SALARY : Double.parseDouble(salaryAsString);
        query = "SELECT e.first_name, e.last_name FROM employees AS e WHERE e.salary > ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setDouble(1, salary);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            System.out.println("No users found!");
        } else {
            do {
                System.out.printf("%s %s%n", rs.getString("first_name"), rs.getString("last_name"));
            } while (rs.next());
        }
    }

    private static void getVillainsNamesHavingMoreThan15Minions() throws SQLException {
        query = "SELECT v.name, COUNT(mv.minion_id) AS `count` FROM villains AS v\n" +
                "JOIN minions_villains mv on v.id = mv.villain_id\n" +
                "GROUP BY mv.villain_id\n" +
                "HAVING count > 15\n" +
                "ORDER BY count DESC";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            System.out.println("There are no villains with so many minions!");
        } else {
            do {
                System.out.printf("%s %d%n", rs.getString("name"), rs.getInt("count"));
            } while (rs.next());
        }
    }

    private static void getVillainAndHisMinions() throws SQLException, IOException {
        System.out.print("Enter villain id: ");
        int id = Integer.parseInt(reader.readLine());
        String villainName = getEntityFromTable(id, "villains");

        if (villainName != null) {
            query = "SELECT m.name, m.age FROM minions AS m\n" +
                    "JOIN minions_villains mv on m.id = mv.minion_id\n" +
                    "WHERE mv.villain_id = ?";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.printf("Villain %s has no minions.%n", villainName);
            } else {
                int counter = 1;

                System.out.printf("Villain: %s%n", villainName);
                do {
                    System.out.printf("%d. %s %d%n", counter, rs.getString("name"), rs.getInt("age"));
                    counter++;
                } while (rs.next());
            }
        } else {
            System.out.printf("No villain with ID %d exists in the database.%n", id);
        }
    }

    private static @Nullable
    String getEntityFromTable(int id, String table) throws SQLException {
        query = "SELECT name FROM " + table + " AS v WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        return rs.next() ? rs.getString("name") : null;
    }

    private static void addMinion() throws IOException, SQLException {
        System.out.print("Enter minion arguments: ");
        String[] minionArgs = reader.readLine().split(REGEX_SPLIT_SPACE);

        System.out.print("Enter villain arguments: ");
        String[] villainArgs = reader.readLine().split(REGEX_SPLIT_SPACE);

        try {
            connection.setAutoCommit(false);
            final String townName = minionArgs[2]; // Expected position in arguments
            final String townsTableName = "towns";

            if (!isPresentInDB(townName, townsTableName)) {
                query = "INSERT INTO " + townsTableName + "(name) VALUE(?)";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, townName);
                stmt.executeUpdate(query);
                System.out.printf("Town %s was added to the database.%n", townName);
            }

            final String villainName = villainArgs[0]; // Expected position in arguments
            final String villainsTableName = "villains";

            if (!isPresentInDB(villainName, villainsTableName)) {
                query = "INSERT INTO " + villainsTableName + "(name, evilness_factor) VALUES (?, ?)";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, villainName);
                stmt.setString(2, DEFAULT_EVILNESS_FACTOR);
                stmt.executeUpdate(query);

                System.out.printf("Villain %s was added to the database.%n", villainName);
            }

            String minionName = minionArgs[0];
            int minionAge = Integer.parseInt(minionArgs[1]);

            int townId = getId(townName, "towns");
            query = "INSERT INTO minions (name, age, town_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, minionName);
            stmt.setInt(2, minionAge);
            stmt.setInt(3, townId);

            stmt.executeUpdate(query);

            int minionId = getId(minionName, "minions");
            int villainId = getId(villainName, "villains");
            query = "INSERT INTO minions_villains (minion_id, villain_id) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, minionId);
            preparedStatement.setInt(2, villainId);
            System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villainName);

            connection.commit();
        } catch (Exception e) {
            System.out.println("Insertion failed during the process. No changes made to the db.");
            connection.rollback();
        }
    }

    private static boolean isPresentInDB(String name, String table) throws SQLException {
        query = "SELECT * FROM " + table + " WHERE name = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, name);

        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    private static int getId(String name, String tableName) throws SQLException {
        query = "SELECT id FROM " + tableName + " WHERE name = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, name);

        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("id");
    }

    private static void updateTownNamesToUppercase() throws IOException, SQLException {
        System.out.print("Enter country name: ");
        String country = reader.readLine();
        query = "UPDATE towns SET name = UPPER(name) WHERE country = ?";

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, country);

        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected == 0) {
            System.out.println("No town names were affected.");
        } else {
            query = "SELECT name FROM towns WHERE country = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, country);

            ResultSet rs = preparedStatement.executeQuery();

            StringBuilder sb = new StringBuilder();
            sb.append("[");

            while (rs.next()) {
                sb.append(rs.getString("name")).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("]");

            System.out.println(sb.toString());
        }
    }

    private static void removeVillain() throws IOException, SQLException {
        System.out.print("Enter id of a villain: ");
        int id = Integer.parseInt(reader.readLine());

        query = "SELECT name FROM villains WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);

        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            System.out.println("No such villain was found");
            return;
        }

        String villainName = rs.getString("name");

        try {
            connection.setAutoCommit(false);
            query = "DELETE FROM minions_villains WHERE villain_id = ?";
            PreparedStatement deleteMVStmt = connection.prepareStatement(query);

            deleteMVStmt.setInt(1, id);

            int deletedMinions = deleteMVStmt.executeUpdate();

            query = "DELETE FROM villains WHERE id = ?";
            PreparedStatement deleteVStmt = connection.prepareStatement(query);
            deleteVStmt.setInt(1, id);

            deleteVStmt.executeUpdate();

            System.out.printf("%s was deleted.%n%d minions released%n", villainName, deletedMinions);
            connection.commit();
        } catch (Exception e) {
            System.out.println("Transaction failed. No changes to the DB were made.");
            connection.rollback();
        }
    }

    private static void printMinionsNames() throws SQLException {
        query = "SELECT name FROM minions";
        PreparedStatement stmt = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = stmt.executeQuery();

        if (rs.last()) {
            int rows = rs.getRow();
            rs.beforeFirst();

            int forwardCounter = 1;
            int backwardsCounter = 0;
            for (int i = 0; i < rows; i++) {
                if (i % 2 == 0) {
                    rs.absolute(forwardCounter);
                    forwardCounter++;
                } else {
                    rs.absolute(rows - backwardsCounter);
                    backwardsCounter++;
                }

                System.out.println(rs.getString("name"));
            }
        }
    }

    private static void increaseMinionsAge() throws IOException, SQLException {
        System.out.print("Enter ids: ");
        String ids = String.join(", ", reader.readLine().split(REGEX_SPLIT_SPACE));

        query = "UPDATE minions SET name = LOWER(name), age = age + 1 WHERE id IN (" + ids + ")";
        Statement stmt = connection.createStatement();

        stmt.executeUpdate(query);

        query = "SELECT name, age FROM minions";
        ResultSet rs = stmt.executeQuery(query);

        if (!rs.next()) {
            System.out.println("No minions in the database!");
        } else {
            do {
                System.out.printf("%s %d%n", rs.getString("name"), rs.getInt("age"));
            } while (rs.next());
        }
    }

    /*
            DELIMITER $$
            CREATE PROCEDURE usp_get_older(minion_id INT)
            BEGIN
                UPDATE minions
                  SET age = age + 1
                WHERE id = minion_id;
            END $$
     */
    private static void increaseAgeWithProcedure() throws IOException, SQLException {
        System.out.print("Enter minion id: ");
        int minionId = Integer.parseInt(reader.readLine());

        query = "CALL usp_get_older(?)";

        CallableStatement callableStatement = connection.prepareCall(query);
        callableStatement.setInt(1, minionId);

        callableStatement.execute();

        query = "SELECT name, age FROM minions WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, minionId);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            System.out.println("There is no such minion with this id!");
        } else {
            System.out.printf("%s %d%n", rs.getString("name"), rs.getInt("age"));
        }
    }
}
