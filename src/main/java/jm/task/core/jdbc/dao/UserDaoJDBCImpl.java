package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String table = "CREATE TABLE `mytestdb`.`users` (`id` INT NOT NULL AUTO_INCREMENT, " +
                "`name` VARCHAR(45) NULL, " +
                "`lastName` VARCHAR(45) NULL, " +
                "`age` INT(3) NULL, " +
                "PRIMARY KEY (`id`));";
        try (PreparedStatement preparedStatement = connection.prepareStatement(table)) {
            preparedStatement.execute();
        } catch (SQLException e) {
        }
    }

    public void dropUsersTable() {
        String table = "DROP TABLE USERS";
        try (PreparedStatement preparedStatement = connection.prepareStatement(table)) {
            preparedStatement.execute();
        } catch (SQLException e) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO USERS(NAME, LASTNAME, AGE) VALUES(?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM USERS WHERE ID = (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        String sql = "SELECT * FROM USERS";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));

                user.setId(resultSet.getLong("id"));

                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM USERS";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
