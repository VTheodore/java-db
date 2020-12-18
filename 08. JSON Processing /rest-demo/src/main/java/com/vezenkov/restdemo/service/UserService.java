package com.vezenkov.restdemo.service;

import com.vezenkov.restdemo.model.User;

import java.util.Collection;

public interface UserService {
    Collection<User> getUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

    User createUser(User user);

    User updateUser(User user);

    User deleteUser(Long id);

    long getUserCount();
}
