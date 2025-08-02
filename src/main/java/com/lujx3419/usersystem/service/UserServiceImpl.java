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
            throw new BusinessException("Username already exists!");
        }

        User user = userMapper.toEntity(request);
        // Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User does not exist!"));

        // Permission check: only the user themselves or admin can update
        if (!SecurityUtil.canAccessUser(existing.getName())) {
            throw new BusinessException("No permission to update this user!");
        }

        existing.setName(request.getName());
        existing.setAge(request.getAge());

        // If the frontend allows updating the password, also encrypt the password
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updated = userRepository.save(existing);
        return userMapper.toResponse(updated);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User does not exist!"));
        
        // Permission check: only the user themselves or admin can view
        if (!SecurityUtil.canAccessUser(user.getName())) {
            throw new BusinessException("No permission to view this user!");
        }
        
        return userMapper.toResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User does not exist!"));
        
        // Permission check: only the user themselves or admin can delete
        if (!SecurityUtil.canAccessUser(user.getName())) {
            throw new BusinessException("No permission to delete this user!");
        }
        
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        // Permission check: only admin can view all users
        if (!SecurityUtil.isAdmin()) {
            throw new BusinessException("Only admin can view all users!");
        }
        
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getUsersByPage(int page, int size) {
        // Permission check: only admin can view paginated user list
        if (!SecurityUtil.isAdmin()) {
            throw new BusinessException("Only admin can view user list!");
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
                .orElseThrow(() -> new BusinessException("User does not exist!"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BusinessException("Old password is incorrect!");
        }

        String newEncodedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(newEncodedPassword);
        userRepository.save(user);
    }

    @Override
    public UserResponse getCurrentUser() {
        String currentUsername = SecurityUtil.getCurrentUsername();
        if (currentUsername == null) {
            throw new BusinessException("User not logged in!");
        }
        
        User user = userRepository.findByName(currentUsername)
                .orElseThrow(() -> new BusinessException("User does not exist!"));
        
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse registerAdmin(AdminRegisterRequest request) {
        // Validate admin registration code
        if (!"ADMIN123".equals(request.getAdminCode())) {
            throw new BusinessException("Admin registration code is incorrect!");
        }

        if (userRepository.findByName(request.getName()).isPresent()) {
            throw new BusinessException("Username already exists!");
        }

        User user = new User();
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ADMIN"); // Set as admin role

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public LoginResponse refreshToken() {
        String currentUsername = SecurityUtil.getCurrentUsername();
        if (currentUsername == null) {
            throw new BusinessException("User not logged in!");
        }
        
        User user = userRepository.findByName(currentUsername)
                .orElseThrow(() -> new BusinessException("User does not exist!"));
        
        String newToken = jwtUtil.generateToken(user.getName());
        UserResponse userResponse = userMapper.toResponse(user);
        
        return new LoginResponse(newToken, userResponse);
    }

}
