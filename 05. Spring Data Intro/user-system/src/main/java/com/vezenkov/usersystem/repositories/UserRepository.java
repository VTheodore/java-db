package com.vezenkov.usersystem.repositories;

import com.vezenkov.usersystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> getUsersByEmailEndingWith(String emailProvider);
}
