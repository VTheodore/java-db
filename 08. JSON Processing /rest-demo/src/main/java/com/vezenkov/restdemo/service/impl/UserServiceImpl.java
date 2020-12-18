package com.vezenkov.restdemo.service.impl;

import com.vezenkov.restdemo.dao.UserRepository;
import com.vezenkov.restdemo.exception.EntityNotFoundException;
import com.vezenkov.restdemo.exception.InvalidEntityException;
import com.vezenkov.restdemo.model.User;
import com.vezenkov.restdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

import static com.vezenkov.restdemo.model.User.ROLE_USER;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d not found.", id)));
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository
                .findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found.", username)));
    }

    @Override
    public User createUser(User user) {
        this.userRepository.findUserByUsername(user.getUsername()).ifPresent(u -> {
            throw new InvalidEntityException(String.format("User with username %s already exists.", user.getUsername()));
        });
        user.setCreated(new Date());
        user.setModified(new Date());

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole(ROLE_USER);
        }
        user.setActive(true);
        return this.userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        user.setModified(new Date());
        return this.userRepository.save(user);
    }

    @Override
    public User deleteUser(Long id) {
        User old = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with id %d not found.", id)));
        this.userRepository.deleteById(id);
        return old;
    }

    @Override
    public long getUserCount() {
        return this.userRepository.count();
    }
}
