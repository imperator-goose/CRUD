package com.ruslan.crudapp.view;

import com.ruslan.crudapp.controller.LabelController;
import com.ruslan.crudapp.model.Label;
import com.ruslan.crudapp.repository.database.JDBCLabelRepository;
import com.ruslan.crudapp.service.LabelService;

import java.sql.SQLException;
import java.util.Scanner;

public class LabelView {
    Scanner scanner;
    private LabelService labelService = new LabelService(new JDBCLabelRepository());
    private LabelController labelController = new LabelController(labelService);

    public void workProgram() {
        while (true) {
            getAllLabels();
            writeNewLabel();
            updateLabel();
            deleteLabel();
            getAllLabels();
            break;
        }
    }

    private void writeNewLabel() {
        scanner = new Scanner(System.in);
        Label labelToSave = new Label();

        System.out.println("Введите название Label");
        String nameLabel = scanner.next();
        System.out.println("Введите id Label");
        Integer idLabel = scanner.nextInt();
        labelToSave.setName(nameLabel);
        labelToSave.setId(idLabel);


        labelController.create(labelToSave);
    }

    private void updateLabel() {

            scanner = new Scanner(System.in);
            Label labelToSave = new Label();
            System.out.println("Введите id Label для обновления ");
            Integer id = scanner.nextInt();
            labelToSave.setId(id);
            System.out.println("Введите новое название Label");
            String nameLabel = scanner.next();
            labelToSave.setName(nameLabel);


            labelController.update(labelToSave);

    }

    private void deleteLabel() {

            scanner = new Scanner(System.in);
            System.out.println("Введите id Label для удаления");
            Integer id = scanner.nextInt();

            labelController.delete(id);

    }
    private void getAllLabels() {
        System.out.println(labelController.readAll());
    }
}
