package com.vezenkov.restdemo.dao;

import com.vezenkov.restdemo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
