package com.ruslan.crudapp.service;

import com.ruslan.crudapp.model.Post;
import com.ruslan.crudapp.repository.PostRepository;

import java.util.List;

public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public Post read(Integer id){
       return postRepository.getById(id);
    }

    public List<Post> readAll(){
        return postRepository.getAll();
    }

    public Post create(Post post){
        postRepository.save(post);
        return post;
    }
    public Post update(Post post){
        postRepository.update(post);
        return post;
    }
    public void delete(Integer id){
        postRepository.deleteById(id);
    }
}
