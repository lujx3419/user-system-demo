package com.lujx3419.usersystem.dto.request;
import jakarta.validation.constraints.NotBlank;

public class UserLoginRequest
 {
    @NotBlank(message = "Username cannot be blank")
    private String name;


    @NotBlank(message = "Password cannot be blank")
    private String password;

    // getters and setters

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}