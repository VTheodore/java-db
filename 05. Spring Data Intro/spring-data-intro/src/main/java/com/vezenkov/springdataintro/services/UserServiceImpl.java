package com.vezenkov.springdataintro.services;

import com.vezenkov.springdataintro.models.User;
import com.vezenkov.springdataintro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        User userInDb = this.userRepository.getUserByUsername(user.getUsername());

        if (userInDb == null) {
            this.userRepository.save(user);
        }else {
            user = userInDb;
        }
    }
}
