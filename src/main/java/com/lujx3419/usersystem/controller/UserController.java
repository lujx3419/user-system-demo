package com.lujx3419.usersystem.controller;

import java.util.List;

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
import com.lujx3419.usersystem.dto.request.ChangePasswordRequest;
import com.lujx3419.usersystem.dto.request.UserLoginRequest;
import com.lujx3419.usersystem.dto.request.UserRegisterRequest;
import com.lujx3419.usersystem.dto.request.UserRequest;
import com.lujx3419.usersystem.dto.response.LoginResponse;
import com.lujx3419.usersystem.dto.response.UserResponse;
import com.lujx3419.usersystem.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse user = userService.registerUser(request);
        return ApiResponse.ok(user);
    }

    @PostMapping("/register/admin")
    public ApiResponse<UserResponse> registerAdmin(@Valid @RequestBody com.lujx3419.usersystem.dto.request.AdminRegisterRequest request) {
        UserResponse user = userService.registerAdmin(request);
        return ApiResponse.ok(user);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        LoginResponse loginResponse = userService.login(request);
        return ApiResponse.ok(loginResponse);
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser() {
        UserResponse user = userService.getCurrentUser();
        return ApiResponse.ok(user);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ApiResponse.ok(user);
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ApiResponse.ok(users);
    }

    @GetMapping("/page")
    public ApiResponse<List<UserResponse>> getUsersByPage(@RequestParam int page, @RequestParam int size) {
        List<UserResponse> users = userService.getUsersByPage(page, size);
        return ApiResponse.ok(users);
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        UserResponse user = userService.updateUser(id, request);
        return ApiResponse.ok(user);
    }

    @PutMapping("/{id}/password")
    public ApiResponse<String> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request);
        return ApiResponse.ok("Password changed successfully!");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.ok("User ID " + id + " deleted!");
    }

    @PostMapping("/refresh-token")
    public ApiResponse<LoginResponse> refreshToken() {
        LoginResponse loginResponse = userService.refreshToken();
        return ApiResponse.ok(loginResponse);
    }
}
