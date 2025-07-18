package com.lujx3419.usersystem.service;

import java.util.List;

import com.lujx3419.usersystem.dto.request.ChangePasswordRequest;
import com.lujx3419.usersystem.dto.request.UserLoginRequest;
import com.lujx3419.usersystem.dto.request.UserRegisterRequest;
import com.lujx3419.usersystem.dto.request.UserRequest;
import com.lujx3419.usersystem.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(Long id, UserRequest request);

    UserResponse getUserById(Long id);

    void deleteUser(Long id);

    List<UserResponse> getAllUsers();

    List<UserResponse> getUsersByPage(int page, int size);


    UserResponse registerUser(UserRegisterRequest request);

 
    UserResponse login(UserLoginRequest request);

    void changePassword(Long userId, ChangePasswordRequest request);
}
