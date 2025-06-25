package com.example.javapro;

import com.example.javapro.model.User;
import com.example.javapro.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceMain.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserService userService) {
        return args -> {
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

        };
    }

}

