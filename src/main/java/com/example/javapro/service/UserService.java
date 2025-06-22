package com.example.javapro.service;


import com.example.javapro.dao.UserDao;
import com.example.javapro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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
