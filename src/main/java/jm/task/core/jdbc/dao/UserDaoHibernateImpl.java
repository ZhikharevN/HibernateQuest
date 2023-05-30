package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSession();
        String table = "CREATE TABLE IF NOT EXISTS `mytestdb`.`users` (`id` INT NOT NULL AUTO_INCREMENT, " +
                "`name` VARCHAR(45) NULL, " +
                "`lastName` VARCHAR(45) NULL, " +
                "`age` INT(3) NULL, " +
                "PRIMARY KEY (`id`));";
        try {
            session.beginTransaction();
            session.createSQLQuery(table).executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            ;
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSession();
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSession();
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSession();
        try {
            session.beginTransaction();
            return session.createQuery("from User").getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSession();
        try {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
