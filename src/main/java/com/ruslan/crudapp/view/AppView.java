package com.ruslan.crudapp.view;

public class AppView {
    LabelView labelView = new LabelView();
    PostView postView = new PostView();
    WriterView writerView = new WriterView();
    public void mainWorkProgram(){
        labelView.workProgram();
        postView.workProgram();
        writerView.workProgram();
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
