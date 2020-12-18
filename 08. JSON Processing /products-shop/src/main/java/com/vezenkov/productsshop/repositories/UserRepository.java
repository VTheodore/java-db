package com.vezenkov.productsshop.repositories;

import com.vezenkov.productsshop.models.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);

    @Query("SELECT DISTINCT u FROM User u JOIN u.productsToSell p WHERE p.buyer IS NOT NULL ORDER BY u.lastName, u.firstName")
    List<User> findAllUsersWithAtLeastOneItemSold();

    @Query("SELECT DISTINCT u FROM User u JOIN u.productsToSell p WHERE p.buyer IS NOT NULL GROUP BY u ORDER BY COUNT(p.id) DESC, u.lastName")
    List<User> findAllUsersWithAtLeastOneItemSoldOrderedByCountAndLastNameDesc();
}
