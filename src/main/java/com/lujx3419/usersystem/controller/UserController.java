package com.lujx3419.usersystem.controller;

import com.lujx3419.usersystem.dto.UserRequest;
import com.lujx3419.usersystem.model.User;
import com.lujx3419.usersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody UserRequest request) {
        return userService.createUser(request.getName(), request.getAge());
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        return userService.updateUser(id, request.getName(), request.getAge());
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "用户 ID " + id + " 已删除！";
    }
}
