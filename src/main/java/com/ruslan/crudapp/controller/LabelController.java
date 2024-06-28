package com.ruslan.crudapp.controller;

import com.ruslan.crudapp.model.Label;
import com.ruslan.crudapp.model.Post;
import com.ruslan.crudapp.model.Status;
import com.ruslan.crudapp.repository.database.JDBCLabelRepository;
import com.ruslan.crudapp.repository.database.JDBCPostRepository;
import com.ruslan.crudapp.service.LabelService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LabelController {
    private LabelService labelService;

    public LabelController(LabelService labelService){
        this.labelService = labelService;
    }

    public Label read(Integer id){
        return labelService.read(id);
    }

    public List<Label> readAll(){
        return labelService.readAll();
    }

    public Label create(String name){
        Label label = new Label();
        label.setName(name);
        labelService.create(label);
        return label;
    }



    public Label update(Label label){
        labelService.update(label);
        return label;
    }
    public void delete(Integer id){
        labelService.delete(id);
    }
}
