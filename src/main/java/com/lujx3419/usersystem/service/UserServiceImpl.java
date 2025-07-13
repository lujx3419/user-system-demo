package com.lujx3419.usersystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lujx3419.usersystem.common.BusinessException;
import com.lujx3419.usersystem.model.User;
import com.lujx3419.usersystem.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(String name, Integer age) {
        
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

    @Override
    public User updateUser(Long id, String name, Integer age) {
        User existing = userRepository.findById(id)
            .orElseThrow(() -> new BusinessException("用户不存在！"));

        existing.setName(name);
        existing.setAge(age);

        // 用 save() 来更新，JPA 会根据 id 是否存在决定 insert 还是 update
        return userRepository.save(existing);
    }
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new BusinessException("用户不存在！"));
    }

    @Override
    public void deleteUser(Long id) {
        User existing = userRepository.findById(id)
            .orElseThrow(() -> new BusinessException("用户不存在！"));
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByPage(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size)).getContent();
    }
}
