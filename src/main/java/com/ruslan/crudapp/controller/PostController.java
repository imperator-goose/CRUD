package com.ruslan.crudapp.controller;

import com.ruslan.crudapp.model.Label;
import com.ruslan.crudapp.model.Post;
import com.ruslan.crudapp.model.Status;
import com.ruslan.crudapp.repository.database.JDBCLabelRepository;
import com.ruslan.crudapp.repository.database.JDBCPostRepository;
import com.ruslan.crudapp.service.PostService;

import java.util.ArrayList;
import java.util.Date;
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

    public Post create(String content,List<Label> labels){
        Post post = new Post();
        post.setContent(content);
        post.setLabels(labels);
        postService.create(post);
        return post;
    }
    public static void main(String[] args) {
        JDBCLabelRepository repo = new JDBCLabelRepository();
        Label label = repo.getById(4);
        JDBCPostRepository repo2 = new JDBCPostRepository();
        PostController postController = new PostController(new PostService(repo2));
        List<Label> labels = new ArrayList<>();
        labels.add(label);
        Post post = repo2.getById(13);
        List<Post> posts = repo2.getAll();
        for (int i = 0;i<posts.size();i++){
            System.out.println(posts.get(i));
        }
    }
    public Post update(Post post){
        postService.update(post);
        return post;
    }
    public void delete(Integer id){
        postService.delete(id);
    }
//    private PostService postService;
//    public PostController(PostService postService){
//        this.postService = postService;
//    }
//    public Post read(Integer id){
//        return postService.read(id);
//    }
//
//    public List<Post> readAll(){
//        return postService.readAll();
//    }
//
//    public Post create(Post post){
//        postService.create(post);
//        return post;
//    }
//    public Post update(Post post){
//        postService.update(post);
//        return post;
//    }
//    public void delete(Integer id){
//        postService.delete(id);
//    }
}
