package com.lujx3419.usersystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")  // Table name can be customized
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Primary key auto-increment
    private Long id;

    private String name;

    private Integer age;

    private String password;  // Reserved for login use
    
    private String role = "USER";  // User role, default is USER

    // -------------------
    // Must have a no-argument constructor
    public User() {}

    // Getter / Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
