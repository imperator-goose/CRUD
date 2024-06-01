package com.ruslan.crudapp.controller;

import com.ruslan.crudapp.model.Post;
import com.ruslan.crudapp.service.PostService;

import java.util.List;

public class PostController {
    private PostService postService;
    public PostController(PostService postService){
        this.postService = postService;
    }
    public Post read(Integer id){
        return postService.read(id);
    }

    public List<Post> readAll(){
        return postService.readAll();
    }

    public Post create(Post post){
        postService.create(post);
        return post;
    }
    public Post update(Post post){
        postService.update(post);
        return post;
    }
    public void delete(Integer id){
        postService.delete(id);
    }
}
