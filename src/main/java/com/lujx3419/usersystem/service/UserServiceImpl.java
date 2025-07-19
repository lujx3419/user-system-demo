package com.lujx3419.usersystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lujx3419.usersystem.common.BusinessException;
import com.lujx3419.usersystem.dto.request.ChangePasswordRequest;
import com.lujx3419.usersystem.dto.request.AdminRegisterRequest;
import com.lujx3419.usersystem.dto.request.UserLoginRequest;
import com.lujx3419.usersystem.dto.request.UserRegisterRequest;
import com.lujx3419.usersystem.dto.request.UserRequest;
import com.lujx3419.usersystem.dto.response.LoginResponse;
import com.lujx3419.usersystem.dto.response.UserResponse;
import com.lujx3419.usersystem.mapper.UserMapper;
import com.lujx3419.usersystem.model.User;
import com.lujx3419.usersystem.repository.UserRepository;
import com.lujx3419.usersystem.common.JwtUtil;
import com.lujx3419.usersystem.common.SecurityUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserResponse createUser(UserRequest request) {
        if (userRepository.findByName(request.getName()).isPresent()) {
            throw new BusinessException("用户名已存在！");
        }

        User user = userMapper.toEntity(request);
        // 加密密码
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在！"));

        // 权限检查：只能修改自己的信息，或者管理员可以修改任何人的信息
        if (!SecurityUtil.canAccessUser(existing.getName())) {
            throw new BusinessException("没有权限修改此用户信息！");
        }

        existing.setName(request.getName());
        existing.setAge(request.getAge());

        // 如果前端允许更新密码，也可以加上密码加密逻辑
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updated = userRepository.save(existing);
        return userMapper.toResponse(updated);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在！"));
        
        // 权限检查：只能查看自己的信息，或者管理员可以查看任何人的信息
        if (!SecurityUtil.canAccessUser(user.getName())) {
            throw new BusinessException("没有权限查看此用户信息！");
        }
        
        return userMapper.toResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("用户不存在！"));
        
        // 权限检查：只能删除自己的账户，或者管理员可以删除任何人的账户
        if (!SecurityUtil.canAccessUser(user.getName())) {
            throw new BusinessException("没有权限删除此用户！");
        }
        
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        // 权限检查：只有管理员可以查看所有用户列表
        if (!SecurityUtil.isAdmin()) {
            throw new BusinessException("只有管理员可以查看所有用户列表！");
        }
        
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getUsersByPage(int page, int size) {
        // 权限检查：只有管理员可以查看分页用户列表
        if (!SecurityUtil.isAdmin()) {
            throw new BusinessException("只有管理员可以查看用户列表！");
        }
        
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
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public LoginResponse login(UserLoginRequest request) {
        User user = userRepository.findByName(request.getName())
                .orElseThrow(() -> new BusinessException("Username does not exist!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("Invalid password!");
        }

        String token = jwtUtil.generateToken(user.getName());
        UserResponse userResponse = userMapper.toResponse(user);
        
        return new LoginResponse(token, userResponse);
    }

    @Override
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在！"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BusinessException("旧密码错误！");
        }

        String newEncodedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(newEncodedPassword);
        userRepository.save(user);
    }

    @Override
    public UserResponse getCurrentUser() {
        String currentUsername = SecurityUtil.getCurrentUsername();
        if (currentUsername == null) {
            throw new BusinessException("用户未登录！");
        }
        
        User user = userRepository.findByName(currentUsername)
                .orElseThrow(() -> new BusinessException("用户不存在！"));
        
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse registerAdmin(AdminRegisterRequest request) {
        // 验证管理员注册码
        if (!"ADMIN123".equals(request.getAdminCode())) {
            throw new BusinessException("管理员注册码错误！");
        }

        if (userRepository.findByName(request.getName()).isPresent()) {
            throw new BusinessException("Username already exists!");
        }

        User user = new User();
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ADMIN"); // 设置为管理员角色

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public LoginResponse refreshToken() {
        String currentUsername = SecurityUtil.getCurrentUsername();
        if (currentUsername == null) {
            throw new BusinessException("用户未登录！");
        }
        
        User user = userRepository.findByName(currentUsername)
                .orElseThrow(() -> new BusinessException("用户不存在！"));
        
        String newToken = jwtUtil.generateToken(user.getName());
        UserResponse userResponse = userMapper.toResponse(user);
        
        return new LoginResponse(newToken, userResponse);
    }

}
