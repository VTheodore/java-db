package com.vezenkov.gamestore.repositories;

import com.vezenkov.gamestore.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User findUserById(@Param("userId") Long id);
}
