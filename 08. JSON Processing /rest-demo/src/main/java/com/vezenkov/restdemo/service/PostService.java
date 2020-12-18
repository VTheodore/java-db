package com.vezenkov.restdemo.service;

import com.vezenkov.restdemo.model.Post;

import java.util.Collection;

public interface PostService {
    Collection<Post> getPosts();

    Post getPostById(Long id);

    Post createPost(Post post);

    Post updatePost(Post post);

    Post deletePost(Long id);

    long getPostCount();
}
