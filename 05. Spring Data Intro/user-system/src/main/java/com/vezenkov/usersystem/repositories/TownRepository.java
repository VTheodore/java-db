package com.vezenkov.usersystem.repositories;

import com.vezenkov.usersystem.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Integer> {
}
