package com.lujx3419.usersystem.service;

import com.lujx3419.usersystem.model.User;
import com.lujx3419.usersystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(String name, Integer age) {
        User user = new User(null, name, age);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, String name, Integer age) {
        User user = new User(id, name, age);
        return userRepository.update(id, user);
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }
}
