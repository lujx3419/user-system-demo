package com.lujx3419.usersystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lujx3419.usersystem.common.BusinessException;
import com.lujx3419.usersystem.dto.request.UserLoginRequest;
import com.lujx3419.usersystem.dto.request.UserRegisterRequest;
import com.lujx3419.usersystem.dto.request.UserRequest;
import com.lujx3419.usersystem.dto.response.UserResponse;
import com.lujx3419.usersystem.mapper.UserMapper;
import com.lujx3419.usersystem.model.User;
import com.lujx3419.usersystem.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponse createUser(UserRequest request) {

        if (userRepository.findByName(request.getName()).isPresent()) {
            throw new BusinessException("用户名已存在！");
        }

        User user = userMapper.toEntity(request);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在！"));

        existing.setName(request.getName());
        existing.setAge(request.getAge());

        User updated = userRepository.save(existing);
        return userMapper.toResponse(updated);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在！"));
        return userMapper.toResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BusinessException("用户不存在！");
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getUsersByPage(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size))
                .getContent()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse registerUser(UserRegisterRequest request) {
        if (userRepository.findByName(request.getName()).isPresent()) {
            throw new BusinessException("Username already exists!");
        }

        User user = new User();
        user.setName(request.getName());
        user.setPassword(request.getPassword()); // TODO: Encrypt in real apps

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse login(UserLoginRequest request) {
        User user = userRepository.findByName(request.getName())
                .orElseThrow(() -> new BusinessException("Username does not exist!"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new BusinessException("Invalid password!");
        }

        return userMapper.toResponse(user);
    }

}
