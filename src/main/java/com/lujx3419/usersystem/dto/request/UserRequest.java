package com.lujx3419.usersystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserRequest {
    @NotBlank(message = "Username cannot be blank")
    private String name;

    @NotNull(message = "Age cannot be null")
    private Integer age;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
