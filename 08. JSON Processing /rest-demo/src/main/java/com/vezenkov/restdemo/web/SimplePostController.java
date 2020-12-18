package com.vezenkov.restdemo.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vezenkov.restdemo.gson.PostGsonDeserializer;
import com.vezenkov.restdemo.gson.PostGsonSerializer;
import com.vezenkov.restdemo.model.Post;
import com.vezenkov.restdemo.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;

@RestController
@RequestMapping("/api/simple")
@Slf4j
public class SimplePostController {
    private final PostService postService;

    private Gson gson;

    @PostConstruct
    private void init() {
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .registerTypeAdapter(Post.class, new PostGsonSerializer())
                .registerTypeAdapter(Post.class, new PostGsonDeserializer())
                .create();
    }

    @Autowired
    public SimplePostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPosts() {
        return gson.toJson(this.postService.getPosts());
    }

//    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> addPost(@RequestBody String body) {
//        log.info("Body received: {}", body);
//        Post post = gson.fromJson(body, Post.class);
//        log.info("Post deserialized: {}", post);
//        Post created = postService.createPost(post);
//        URI uri = MvcUriComponentsBuilder
//                .fromMethodName(SimplePostController.class,"addPost", post)
//                .pathSegment("{id}").buildAndExpand(created.getId()).toUri();
//        return ResponseEntity.created(uri).body(gson.toJson(created));
//    }
}
