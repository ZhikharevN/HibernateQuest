package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    final static String URL = "jdbc:mysql://localhost:3306/mytestdb";
    final static String USERNAME = "myServer";
    final static String PASSWORD = "2442";
    private static SessionFactory sessionFactory;

    public Util() {

    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSession() {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USERNAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                Configuration configuration = new Configuration().setProperties(settings).addAnnotatedClass(User.class);

                sessionFactory = configuration.buildSessionFactory();
                return sessionFactory;
            } catch (RuntimeException e) {
                System.out.println("Warning, sessionFactory don't created!");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
