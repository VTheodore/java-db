package com.vezenkov.restdemo.init;

import com.vezenkov.restdemo.model.Post;
import com.vezenkov.restdemo.model.User;
import com.vezenkov.restdemo.service.PostService;
import com.vezenkov.restdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final PostService postService;

    private final UserService userService;

    private static final List<Post> SAMPLE_POSTS = List.of(
            new Post("Welcome to Spring Data", "Developing data access object with Spring Data is easy ...",
                    "https://www.publicdomainpictures.net/pictures/320000/velka/rosa-klee-blute-blume.jpg"),
            new Post("Reactive Spring Data", "Check R2DBC for reactive JDBC API ...",
                    "https://www.publicdomainpictures.net/pictures/70000/velka/spring-grass-in-sun-light.jpg"),
            new Post("New in Spring 5", "Webflux provides reactive and non-blocking web service implemntation ...",
                    "https://www.publicdomainpictures.net/pictures/320000/velka/blute-blumen-garten-bluhen-1577191608UTW.jpg"),
            new Post("Beginnig REST with Spring 5", "Spring MVC and WebFlux make implemeting RESTful services really easy ...",
                    "https://www.publicdomainpictures.net/pictures/20000/velka/baby-lamb.jpg")
    );

    private static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "admin", "admin", User.ROLE_ADMIN),
            new User("Ivan", "Pertov", "ivan", "ivan", User.ROLE_USER)
    );

    @Autowired
    public DataInitializer(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        SAMPLE_USERS.forEach(this.userService::createUser);
        log.info("Created users: {}", this.userService.getUsers());

        SAMPLE_POSTS.forEach(post -> {
            post.setAuthor(this.userService.getUserById(1L));
            this.postService.createPost(post);
        });
        log.info("Created posts: {}", this.postService.getPosts());
    }
}
