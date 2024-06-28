package com.ruslan.crudapp.view;

import java.util.Scanner;

public class AppView {
    LabelView labelView = new LabelView();
    PostView postView = new PostView();
    WriterView writerView = new WriterView();
    public void mainWorkProgram(){
        Scanner scanner = new Scanner(System.in);
        boolean stopper = true;
        while (stopper) {
            System.out.println("Продолжить?");
            stopper = scanner.nextBoolean();
            if (stopper==false){
                break;
            }
            System.out.println("Выберите класс для работы: 1 - Writer, 2 - Post, 3 - Label");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    writerView.workProgram();
                    break;
                case 2:
                    postView.workProgram();
                    break;
                case 3:
                    labelView.workProgram();
                    break;
                default:
                    System.out.println("Некорректный выбор");
            }
        }
    }

    public static AppView appView;

    private AppView(){}

    public static AppView getAppView(){
        if (appView == null){
            appView = new AppView();
        }
        return appView;
    }
}
