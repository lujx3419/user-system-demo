package com.lujx3419.usersystem.mapper;

import org.mapstruct.Mapper;

import com.lujx3419.usersystem.dto.request.UserRequest;
import com.lujx3419.usersystem.dto.response.UserResponse;
import com.lujx3419.usersystem.model.User;

@Mapper(componentModel = "spring")   
public interface UserMapper {

    UserResponse toResponse(User user);

    User toEntity(UserRequest userRequest);

}
