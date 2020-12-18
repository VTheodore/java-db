package com.vezenkov.restdemo.service.impl;

import com.vezenkov.restdemo.dao.PostRepository;
import com.vezenkov.restdemo.exception.EntityNotFoundException;
import com.vezenkov.restdemo.exception.InvalidEntityException;
import com.vezenkov.restdemo.model.Post;
import com.vezenkov.restdemo.model.User;
import com.vezenkov.restdemo.service.PostService;
import com.vezenkov.restdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    private final UserService userService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public Collection<Post> getPosts() {
        return this.postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return this.postRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with id %d not found.", id)));
    }

    @Override
    public Post createPost(Post post) {
        Long authorId;

        if (post.getAuthor() != null && post.getAuthor().getId() != null) {
            authorId = post.getAuthor().getId();
        } else {
            authorId = post.getAuthorId();
        }

        if (authorId != null) {
            User author = this.userService.getUserById(authorId);
            post.setAuthor(author);
        }

        if (post.getCreated() == null) {
            post.setCreated(new Date());
        }

        post.setModified(new Date());
        return this.postRepository.save(post);
    }

    @Transactional
    @Override
    public Post updatePost(Post post) {
        post.setModified(new Date());
        Post old = this.getPostById(post.getId());
        if(post.getAuthor() != null && !post.getAuthor().getId().equals(old.getAuthor().getId()))
            throw new InvalidEntityException("Author of article could not ne changed");
        post.setAuthor(old.getAuthor());
        return this.postRepository.save(post);
    }

    @Override
    public Post deletePost(Long id) {
        Post old = this.postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Post with id %d not found.", id)));
        this.postRepository.deleteById(id);
        return old;
    }

    @Override
    public long getPostCount() {
        return this.postRepository.count();
    }
}
