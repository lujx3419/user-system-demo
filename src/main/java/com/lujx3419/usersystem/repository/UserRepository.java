package com.lujx3419.usersystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lujx3419.usersystem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // 如果你还想保留按名字查找
    Optional<User> findByName(String name);

    // 其他方法（findById, findAll, save, deleteById）JpaRepository 已经自动提供了
}
