package com.example.javapro.service;


import com.example.javapro.config.AppConfig;
import com.example.javapro.dao.UserDao;
import com.example.javapro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        try {
            // Создание пользователей
            User user1 = userService.createUser("Max");
            User user2 = userService.createUser("Tom");
            System.out.println("Created users: " + user1 + ", " + user2);

            // Получение всех пользователей
            System.out.println("All users:");
            userService.getAllUsers().forEach(System.out::println);

            // Получение одного пользователя
            User foundUser = userService.getUserById(user1.getId());
            System.out.println("Found user: " + foundUser);

            // Обновление пользователя
            foundUser.setUsername("Bob");
            userService.updateUser(foundUser);
            System.out.println("After update: " + userService.getUserById(foundUser.getId()));

            // Удаление пользователя
            userService.deleteUser(user2.getId());
            System.out.println("After deletion, all users:");
            userService.getAllUsers().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            context.close();
        }
    }

    public User createUser(String username) throws SQLException {
        User user = new User(null, username);
        return userDao.create(user);
    }

    public User getUserById(Long id) throws SQLException {
        return userDao.findById(id);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDao.findAll();
    }

    public void updateUser(User user) throws SQLException {
        userDao.update(user);
    }

    public void deleteUser(Long id) throws SQLException {
        userDao.delete(id);
    }
}
