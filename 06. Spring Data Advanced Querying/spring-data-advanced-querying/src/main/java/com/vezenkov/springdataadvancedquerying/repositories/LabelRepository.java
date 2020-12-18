package com.vezenkov.springdataadvancedquerying.repositories;

import com.vezenkov.springdataadvancedquerying.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    Label findLabelById(Long id);
}
