package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Util {

    final static String URL = "jdbc:mysql://localhost:3306/mytestdb";
    final static String USERNAME = "myServer";
    final static String PASSWORD = "2442";

    public Util() {

    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
