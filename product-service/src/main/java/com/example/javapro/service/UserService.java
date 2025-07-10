package com.example.javapro.service;


import com.example.javapro.model.User;
import com.example.javapro.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public User createUser(String username) throws SQLException {
        User user = new User();
        user.setUsername(username);
        return userRepo.save(user);
    }

    public User getUserById(Long id) throws SQLException {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Iterable<User> getAllUsers() throws SQLException {
        return userRepo.findAll();
    }

    @Transactional
    public void updateUser(User user) throws SQLException {
        userRepo.save(user);
    }

    @Transactional
    public void deleteUser(Long id) throws SQLException {
        userRepo.deleteById(id);
    }
}
