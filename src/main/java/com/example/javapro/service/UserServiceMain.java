package com.example.javapro.service;

import com.example.javapro.model.ProductDto;
import com.example.javapro.model.ProductType;
import com.example.javapro.model.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserServiceMain implements CommandLineRunner {

    private final UserService userService;
    private final ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceMain.class);

    public UserServiceMain(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    @Transactional
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

        ProductDto product1 = new ProductDto(1L, "40817810099910001111",
                new BigDecimal("1000.00"), ProductType.ACCOUNT, 1L);
        productService.createProduct(product1);
        logger.info("Creating products {}", product1);

        productService.getUserProducts(1L).forEach(user -> logger.info(String.valueOf(user)));


    }
}
