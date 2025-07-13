package com.lujx3419.usersystem.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lujx3419.usersystem.common.ApiResponse;
import com.lujx3419.usersystem.dto.UserRequest;
import com.lujx3419.usersystem.dto.UserResponse;
import com.lujx3419.usersystem.model.User;
import com.lujx3419.usersystem.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        User user = userService.createUser(request.getName(), request.getAge());
        return ApiResponse.ok(new UserResponse(user));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        User user = userService.updateUser(id, request.getName(), request.getAge());
        return ApiResponse.ok(new UserResponse(user));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ApiResponse.ok(new UserResponse(user));
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> result = users.stream().map(UserResponse::new).collect(Collectors.toList());
        return ApiResponse.ok(result);
    }

    @GetMapping("/page")
    public ApiResponse<List<UserResponse>> getUsersByPage(@RequestParam int page, @RequestParam int size) {
        List<User> users = userService.getUsersByPage(page, size);
        List<UserResponse> result = users.stream().map(UserResponse::new).collect(Collectors.toList());
        return ApiResponse.ok(result);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.ok("用户 ID " + id + " 已删除！");
    }
}
