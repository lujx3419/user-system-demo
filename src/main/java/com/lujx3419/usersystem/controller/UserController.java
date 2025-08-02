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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "APIs related to user management")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Create User", description = "Create a new user")
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse user = userService.createUser(request);
        return ApiResponse.ok(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update User", description = "Update an existing user")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        UserResponse user = userService.updateUser(id, request);
        return ApiResponse.ok(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User", description = "Get user by ID")
    public ApiResponse<UserResponse> getUser(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ApiResponse.ok(user);
    }

    @GetMapping
    @Operation(summary = "Get All Users", description = "Get all users (admin only)", security = @SecurityRequirement(name = "bearer-jwt"))
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ApiResponse.ok(users);
    }

    @GetMapping("/page")
    @Operation(summary = "Get Users by Page", description = "Get users with pagination (admin only)", security = @SecurityRequirement(name = "bearer-jwt"))
    public ApiResponse<List<UserResponse>> getUsersByPage(@RequestParam int page, @RequestParam int size) {
        List<UserResponse> users = userService.getUsersByPage(page, size);
        return ApiResponse.ok(users);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User", description = "Delete user by ID")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.ok("User ID " + id + " deleted!");
    }

    @PostMapping("/register")
    @Operation(summary = "User Registration", description = "Register a new user")
    public ApiResponse<UserResponse> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse user = userService.registerUser(request);
        return ApiResponse.ok(user);
    }

    @PostMapping("/register/admin")
    @Operation(summary = "Admin Registration", description = "Register an admin account (requires admin code)")
    public ApiResponse<UserResponse> registerAdmin(@Valid @RequestBody com.lujx3419.usersystem.dto.request.AdminRegisterRequest request) {
        UserResponse user = userService.registerAdmin(request);
        return ApiResponse.ok(user);
    }

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "User login and return JWT token")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        LoginResponse loginResponse = userService.login(request);
        return ApiResponse.ok(loginResponse);
    }

    @PutMapping("{id}/password")
    @Operation(summary = "Change Password", description = "Change user password")
    public ApiResponse<String> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request);
        return ApiResponse.ok("Password changed successfully!");
    }

    @GetMapping("/me")
    @Operation(summary = "Get Current User", description = "Get details of the currently logged-in user")
    public ApiResponse<UserResponse> getCurrentUser() {
        UserResponse user = userService.getCurrentUser();
        return ApiResponse.ok(user);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh Token", description = "Get a new JWT token")
    public ApiResponse<LoginResponse> refreshToken() {
        LoginResponse loginResponse = userService.refreshToken();
        return ApiResponse.ok(loginResponse);
    }
}
