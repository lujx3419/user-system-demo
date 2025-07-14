package com.lujx3419.usersystem.service;

import java.util.List;

import com.lujx3419.usersystem.dto.UserRequest;
import com.lujx3419.usersystem.dto.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(Long id, UserRequest request);

    UserResponse getUserById(Long id);

    void deleteUser(Long id);

    List<UserResponse> getAllUsers();

    List<UserResponse> getUsersByPage(int page, int size);
}
