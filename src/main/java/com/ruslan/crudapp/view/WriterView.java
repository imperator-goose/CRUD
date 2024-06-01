package com.ruslan.crudapp.view;

import com.ruslan.crudapp.controller.LabelController;
import com.ruslan.crudapp.controller.PostController;
import com.ruslan.crudapp.controller.WriterController;
import com.ruslan.crudapp.model.Label;
import com.ruslan.crudapp.model.Post;
import com.ruslan.crudapp.model.Status;
import com.ruslan.crudapp.model.Writer;
import com.ruslan.crudapp.repository.database.JDBCLabelRepository;
import com.ruslan.crudapp.repository.database.JDBCPostRepository;
import com.ruslan.crudapp.repository.database.JDBCWriterRepository;
import com.ruslan.crudapp.service.LabelService;
import com.ruslan.crudapp.service.PostService;
import com.ruslan.crudapp.service.WriterService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class WriterView {

    Scanner scanner;
    private PostService postService = new PostService(new JDBCPostRepository());
    private PostController postController = new PostController(postService);
    private WriterService writerService = new WriterService(new JDBCWriterRepository());
    private WriterController writerController = new WriterController(writerService);

    public void workProgram() {
        while (true) {
            getAllWriters();
            writeNewWriter();
            updateWriter();
            deleteWriter();
            getAllWriters();
            break;
        }
    }

    private void writeNewWriter() {
        int id;
        scanner = new Scanner(System.in);
        System.out.println("Введите id");
        id = scanner.nextInt();
        Writer writerToSave = new Writer();
        writerToSave.setId(id);
        System.out.println("Введите firstName");
        String firstName = scanner.next();
        System.out.println();
        writerToSave.setFirstName(firstName);
        System.out.println("Введите lastName");
        String lastName = scanner.next();
        writerToSave.setLastName(lastName);
        List<Post> posts = new ArrayList<>();
        Post post = null;
        System.out.println("Введите id post");
        boolean stopper = true;

        while (stopper){
            id = scanner.nextInt();
            post = postController.read(id);
            posts.add(post);
            System.out.println("Введите False если хотите выйти из цикла");
            stopper = scanner.nextBoolean();
        }
        writerToSave.setPosts(posts);

        writerController.create(writerToSave);
    }

    private void updateWriter() {

        scanner = new Scanner(System.in);
        Writer writerToSave = new Writer();
        System.out.println("Введите id Writer для обновления ");
        int id = scanner.nextInt();
        writerToSave.setId(id);
        System.out.println("Введите новый firstName");
        String firstName = scanner.next();
        writerToSave.setFirstName(firstName);
        System.out.println("Введите новый lastName");
        String lastName = scanner.next();
        writerToSave.setLastName(lastName);
        List<Post> posts = new ArrayList<>();
        Post post = null;
        System.out.println("Введите id post");
        boolean stopper = true;

        while (stopper){
            id = scanner.nextInt();
            post = postController.read(id);
            posts.add(post);
            System.out.println("Введите False если хотите выйти из цикла");
            stopper = scanner.nextBoolean();
        }
        writerToSave.setPosts(posts);

        writerController.update(writerToSave);

    }

    private void deleteWriter() {

        scanner = new Scanner(System.in);
        System.out.println("Введите id Writer для удаления");
        int id = scanner.nextInt();

        writerController.delete(id);

    }
    private void getAllWriters() {
        System.out.println(writerController.readAll());
    }
}
