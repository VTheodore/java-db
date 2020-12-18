package com.vezenkov.springdataintro.services;

import com.vezenkov.springdataintro.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void registerUser(User user);
}
