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
        Scanner scanner = new Scanner(System.in);
        boolean stopper = true;
        while(stopper) {
            System.out.println("Продолжить?");
            stopper = scanner.nextBoolean();
            if (stopper==false){
                break;
            }
            System.out.println("Выберите метод для работы: 1 - writeNewPost, 2 - updatePost, 3 - deletePost, 4 - getAllPosts");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    writeNewPost();
                    break;
                case 2:
                    updatePost();
                    break;
                case 3:
                    deletePost();
                    break;
                case 4:
                    getAllPosts();
                    break;
                default:
                    System.out.println("Некорректный выбор");
            }
        }
    }

    private void writeNewPost() {
        scanner = new Scanner(System.in);
        System.out.println("Введите content");
        String content = scanner.next();
        List<Label> labels = new ArrayList<>();
        Label label = null;
        System.out.println("Введите id labels");
        boolean stopper = true;
        int id;
        while (stopper){
            id = scanner.nextInt();
            label = labelController.read(id);
            labels.add(label);
            System.out.println("Введите False если хотите выйти из цикла");
            stopper = scanner.nextBoolean();
        }


        postController.create(content,labels);
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
