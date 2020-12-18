package com.vezenkov.usersystem.repositories;

import com.vezenkov.usersystem.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {
}
