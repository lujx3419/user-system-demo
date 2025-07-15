package com.lujx3419.usersystem.dto.response;

import com.lujx3419.usersystem.model.User;

public class UserResponse {
    private Long id;
    private String name;
    private Integer age;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
    }

    // Getter
    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getAge() { return age; }
}
