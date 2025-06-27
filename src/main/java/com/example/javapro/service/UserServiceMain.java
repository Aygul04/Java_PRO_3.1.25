package com.example.javapro.service;

import com.example.javapro.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserServiceMain implements CommandLineRunner {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceMain.class);

    public UserServiceMain(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = userService.createUser("Max");
        User user2 = userService.createUser("Tom");
        logger.info("Created users: " + user1 + ", " + user2);

        // Получение всех пользователей
        logger.info("All users:");
        userService.getAllUsers().forEach(user -> logger.info(String.valueOf(user)));

        // Получение одного пользователя
        User foundUser = userService.getUserById(user1.getId());
        logger.info("Found user: " + foundUser);

        // Обновление пользователя
        foundUser.setUsername("Bob");
        userService.updateUser(foundUser);
        logger.info("After update: " + userService.getUserById(foundUser.getId()));

        // Удаление пользователя
        userService.deleteUser(user2.getId());
        logger.info("After deletion, all users:");
        userService.getAllUsers().forEach(user -> logger.info(String.valueOf(user)));

    }
}
