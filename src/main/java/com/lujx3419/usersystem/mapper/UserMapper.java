package com.lujx3419.usersystem.mapper;

import com.lujx3419.usersystem.dto.request.UserRequest;
import com.lujx3419.usersystem.dto.response.UserResponse;
import com.lujx3419.usersystem.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserRequest request);

    UserResponse toResponse(User user);
}
