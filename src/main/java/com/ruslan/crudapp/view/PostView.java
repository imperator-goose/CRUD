package com.ruslan.crudapp.view;

import com.ruslan.crudapp.controller.LabelController;
import com.ruslan.crudapp.controller.PostController;
import com.ruslan.crudapp.model.Label;
import com.ruslan.crudapp.model.Post;
import com.ruslan.crudapp.model.Status;
import com.ruslan.crudapp.repository.database.JDBCLabelRepository;
import com.ruslan.crudapp.repository.database.JDBCPostRepository;
import com.ruslan.crudapp.service.LabelService;
import com.ruslan.crudapp.service.PostService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PostView {
    Scanner scanner;
    private PostService postService = new PostService(new JDBCPostRepository());
    private PostController postController = new PostController(postService);
    private LabelService labelService = new LabelService(new JDBCLabelRepository());
    private LabelController labelController = new LabelController(labelService);

    public void workProgram() {
        while (true) {
            getAllPosts();
            writeNewPost();
            updatePost();
            deletePost();
            getAllPosts();
            break;
        }
    }

    private void writeNewPost() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");
        String formattedDate = sdf.format(date);
        float created = Float.parseFloat(formattedDate);
        int id;
        scanner = new Scanner(System.in);
        System.out.println("Введите id");
        id = scanner.nextInt();
        Post postToSave = new Post();
        postToSave.setId(id);
        postToSave.setCreated(created);
        postToSave.setUpdated(created);
        System.out.println("Введите content");
        String content = scanner.next();
        postToSave.setStatus(Status.ACTIVE);
        postToSave.setContent(content);
        List<Label> labels = new ArrayList<>();
        Label label = null;
        System.out.println("Введите id labels");
        boolean stopper = true;

        while (stopper){
            id = scanner.nextInt();
            label = labelController.read(id);
            labels.add(label);
            System.out.println("Введите False если хотите выйти из цикла");
            stopper = scanner.nextBoolean();
        }
        postToSave.setLabels(labels);

        postController.create(postToSave);
    }

    private void updatePost() {

        scanner = new Scanner(System.in);
        Post postToSave = new Post();
        System.out.println("Введите id Post для обновления ");
        Integer id = scanner.nextInt();
        postToSave.setId(id);
        System.out.println("Введите новый content");
        String content = scanner.next();
        postToSave.setContent(content);

        postController.update(postToSave);

    }

    private void deletePost() {

        scanner = new Scanner(System.in);
        System.out.println("Введите id Post для удаления");
        int id = scanner.nextInt();

        postController.delete(id);

    }
    private void getAllPosts() {
        System.out.println(postController.readAll());
    }
}
