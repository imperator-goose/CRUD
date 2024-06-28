package com.ruslan.crudapp.controller;

import com.ruslan.crudapp.model.Post;
import com.ruslan.crudapp.model.Writer;
import com.ruslan.crudapp.repository.WriterRepository;
import com.ruslan.crudapp.repository.database.JDBCPostRepository;
import com.ruslan.crudapp.repository.database.JDBCWriterRepository;
import com.ruslan.crudapp.service.WriterService;

import java.util.ArrayList;
import java.util.List;

public class WriterController {
    private WriterService writerService;

    public WriterController(WriterService writerService){
        this.writerService = writerService;
    }

    public List<Writer> readAll(){
        return writerService.readAll();
    }

    public Writer read(Integer id){
        return writerService.read(id);
    }
    public Writer create(String firstName,String lastName,List<Post> posts){
        Writer writer = new Writer();
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        writer.setPosts(posts);
        writerService.create(writer);
        return writer;
    }

    public static void main(String[] args) {
        JDBCWriterRepository repo = new JDBCWriterRepository();
        WriterController writerController = new WriterController(new WriterService(repo));
        JDBCPostRepository repo2 = new JDBCPostRepository();
//        List<Writer> list = repo.getAll();
//        for (int i = 0;i<list.size();i++){
//            System.out.println(list.get(i));
//        }
        List<Post> posts2 = new ArrayList<>();
        Post post = repo2.getById(11);
        posts2.add(post);
        Writer writer = writerController.create("ivan","ivanov",posts2);

    }
    public Writer update(Writer writer){
        writerService.update(writer);
        return writer;
    }
    public void delete(Integer id){
        writerService.delete(id);
    }
}
