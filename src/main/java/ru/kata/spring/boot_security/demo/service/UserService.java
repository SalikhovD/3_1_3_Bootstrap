package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {
    void add(User user);

    void deleteUser(Long id);

    void saveOrUpdate(User user);

    List<User> listUsers();

    User getUser(Long id);

    User findByUsername(String username);

    void preCreateUsers();
}
