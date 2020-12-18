package com.vezenkov.cardealer.repositories;

import com.vezenkov.cardealer.model.entitites.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    Part findPartByName(String name);

    Part findPartById(Long id);
}
