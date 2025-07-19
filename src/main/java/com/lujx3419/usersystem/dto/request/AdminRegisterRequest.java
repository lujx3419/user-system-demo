package com.lujx3419.usersystem.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AdminRegisterRequest {
    @NotBlank(message = "Username cannot be blank")
    private String name;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "Admin code cannot be blank")
    private String adminCode;

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }
} 