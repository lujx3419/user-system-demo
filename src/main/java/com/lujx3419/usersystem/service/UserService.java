package com.lujx3419.usersystem.service;

import java.util.List;

import com.lujx3419.usersystem.model.User;

public interface UserService {
    User createUser(String name, Integer age);
    User updateUser(Long id, String name, Integer age);
    User getUserById(Long id);
    void deleteUser(Long id);
    List<User> getAllUsers();
    List<User> getUsersByPage(int page, int size);
}
