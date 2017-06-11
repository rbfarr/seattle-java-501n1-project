package com.codefellows;

import com.codefellows.dbmodels.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Properties;

/**
 * Created by bfarr on 6/8/17.
 */
public class ZooUtils {
    private static final String DB_HOST = "seattle-java-501n1-project-mariadb.cdvwhjbcai8i.us-west-2.rds.amazonaws.com:3306";
    private static final String DB_USER = "codefellows";
    private static final String DB_PASS = "codefellows";
    private static final String DB_NAME = "codefellows";

    private static final Logger LOG = LoggerFactory.getLogger(ZooUtils.class);

    public static Connection getConnection() {
        Connection conn = null;

        try {
            Properties connectionProps = new Properties();

            connectionProps.put("user", DB_USER);
            connectionProps.put("password", DB_PASS);

            conn = DriverManager.getConnection("jdbc:mysql://" + DB_HOST + "/" + DB_NAME, connectionProps);
            LOG.info("Connected to database");
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return conn;
    }

    public static void setPersonAge(Connection con, String personName, int age) throws SQLException {
        Statement stmt = null;
        String query = "UPDATE person SET age=" + age + " WHERE name=\'" + personName + "\'";

        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e ) {
            LOG.error(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static int getPersonAge(Connection con, String personName) throws SQLException {
        Statement stmt = null;
        String query = "SELECT name,age FROM person";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");

                if (name.equals(personName)) {
                    return age;
                }
            }
        } catch (SQLException e ) {
            LOG.error(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }

        return -1;
    }

    public static String viewTable(Connection con) throws SQLException {
        Statement stmt = null;
        String query = "SELECT name,age FROM person";

        StringBuilder stringBuilder = new StringBuilder();

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");

                stringBuilder.append("<p>" + new Person(name, age) + "</p>");
            }
        } catch (SQLException e ) {
            LOG.error(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }

        return stringBuilder.toString();
    }
}
