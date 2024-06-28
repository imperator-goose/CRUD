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
        Scanner scanner = new Scanner(System.in);
        boolean stopper = true;
        while(stopper) {
            System.out.println("Продолжить?");
            stopper = scanner.nextBoolean();
            if (stopper==false){
                break;
            }
            System.out.println("Выберите метод для работы: 1 - writeNewLabel, 2 - updateLabel, 3 - deleteLabel, 4 - getAllLabels");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    writeNewLabel();
                    break;
                case 2:
                    updateLabel();
                    break;
                case 3:
                    deleteLabel();
                    break;
                case 4:
                    getAllLabels();
                    break;
                default:
                    System.out.println("Некорректный выбор");
            }
        }
    }

    private void writeNewLabel() {
        scanner = new Scanner(System.in);
        String labelName = null;

        System.out.println("Введите название Label");
        labelName = scanner.next();


        labelController.create(labelName);
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
