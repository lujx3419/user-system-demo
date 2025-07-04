package com.lujx3419.usersystem.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.lujx3419.usersystem.model.User;

@Repository
public class UserRepository {

    private final Map<Long, User> userDb = new ConcurrentHashMap<>();
    private Long idCounter = 1L;

    public User save(User user) {
        user.setId(idCounter++);
        userDb.put(user.getId(), user);
        return user;
    }

    public Optional<User> findByName(String name) {
        return userDb.values()
                .stream()
                .filter(u -> u.getName().equals(name))
                .findFirst();
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userDb.get(id));
    }

    public List<User> findAll() {
        return new ArrayList<>(userDb.values());
    }

    public User update(Long id, User updated) {
        updated.setId(id);
        userDb.put(id, updated);
        return updated;
    }

    public void delete(Long id) {
        userDb.remove(id);
    }
}
