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
import com.lujx3419.usersystem.dto.request.AdminRegisterRequest;
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
@Tag(name = "用户管理", description = "用户相关的API接口")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse user = userService.createUser(request);
        return ApiResponse.ok(user);
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        UserResponse user = userService.updateUser(id, request);
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

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.ok("用户 ID " + id + " 已删除！");
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "创建新用户账户")
    public ApiResponse<UserResponse> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse user = userService.registerUser(request);
        return ApiResponse.ok(user);
    }

    @PostMapping("/register/admin")
    @Operation(summary = "管理员注册", description = "创建管理员账户（需要管理员注册码）")
    public ApiResponse<UserResponse> registerAdmin(@Valid @RequestBody AdminRegisterRequest request) {
        UserResponse user = userService.registerAdmin(request);
        return ApiResponse.ok(user);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录并返回JWT令牌")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        LoginResponse loginResponse = userService.login(request);
        return ApiResponse.ok(loginResponse);
    }

    @PutMapping("{id}/password")
    public ApiResponse<String> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request);
        return ApiResponse.ok("密码修改成功！");
    }

    @GetMapping("/me")
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户的详细信息")
    public ApiResponse<UserResponse> getCurrentUser() {
        UserResponse user = userService.getCurrentUser();
        return ApiResponse.ok(user);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "刷新令牌", description = "获取新的JWT令牌")
    public ApiResponse<LoginResponse> refreshToken() {
        LoginResponse loginResponse = userService.refreshToken();
        return ApiResponse.ok(loginResponse);
    }

}
