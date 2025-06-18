package com.example.javapro.dao;

import com.example.javapro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {
    private final DataSource dataSource;

    @Autowired
    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User create(User user) throws SQLException {
        String sql = "INSERT INTO users (username) VALUES (?) RETURNING id";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                return user;
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }

    public User findById(Long id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getLong("id"),
                        resultSet.getString("username")
                );
            } else {
                return null;
            }
        }
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("username")
                ));
            }
        }
        return users;
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE users SET username = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            statement.setLong(2, user.getId());
            statement.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}