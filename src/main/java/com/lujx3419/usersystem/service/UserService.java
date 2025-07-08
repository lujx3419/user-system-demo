package com.lujx3419.usersystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

        User user = new User();
        user.setName(name);
        user.setAge(age);
        // password 先留空，后面做注册/登录时再用
        return userRepository.save(user);
    }

    public User updateUser(Long id, String name, Integer age) {
        User existing = userRepository.findById(id)
            .orElseThrow(() -> new BusinessException("用户不存在！"));

        existing.setName(name);
        existing.setAge(age);

        // 用 save() 来更新，JPA 会根据 id 是否存在决定 insert 还是 update
        return userRepository.save(existing);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new BusinessException("用户不存在！"));
    }

    public void deleteUser(Long id) {
        User existing = userRepository.findById(id)
            .orElseThrow(() -> new BusinessException("用户不存在！"));
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByPage(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size)).getContent();
    }
}
