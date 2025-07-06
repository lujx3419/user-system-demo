package com.lujx3419.usersystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lujx3419.usersystem.common.BusinessException;
import com.lujx3419.usersystem.model.User;
import com.lujx3419.usersystem.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(String name, Integer age) {
        if (name == null || name.trim().isEmpty()) {
            throw new BusinessException("用户名不能为空！");
        }
        if (age == null || age < 0 || age > 120) {
            throw new BusinessException("年龄不合法！");
        }
        Optional<User> exists = userRepository.findByName(name);
        if (exists.isPresent()) {
            throw new BusinessException("用户名已存在！");
        }

        User user = new User(null, name, age);
        return userRepository.save(user);
    }

    public User updateUser(Long id, String name, Integer age) {
        User existing = userRepository.findById(id)
            .orElseThrow(() -> new BusinessException("用户不存在！"));

        existing.setName(name);
        existing.setAge(age);

        return userRepository.update(id, existing);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new BusinessException("用户不存在！"));
    }

    public void deleteUser(Long id) {
        User existing = userRepository.findById(id)
            .orElseThrow(() -> new BusinessException("用户不存在！"));
        userRepository.delete(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByPage(int page, int size) {
        List<User> all = userRepository.findAll();
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, all.size());
        if (fromIndex > toIndex) {
            return List.of();
        }
        return all.subList(fromIndex, toIndex);
    }
}
