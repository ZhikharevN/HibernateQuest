package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSession();


    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        String table = "CREATE TABLE IF NOT EXISTS `mytestdb`.`users` (`id` INT NOT NULL AUTO_INCREMENT, " +
                "`name` VARCHAR(45) NULL, " +
                "`lastName` VARCHAR(45) NULL, " +
                "`age` INT(3) NULL, " +
                "PRIMARY KEY (`id`));";
        try {
            session.beginTransaction();
            session.createSQLQuery(table).executeUpdate();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createQuery(String.format("delete User where id = %d", id));
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        List<User> list = null;
        try {
            session.beginTransaction();
            list = session.createQuery("from User").getResultList();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
