package com.ruslan.crudapp.service;

import com.ruslan.crudapp.model.Label;
import com.ruslan.crudapp.repository.LabelRepository;

import java.util.List;

public class LabelService {
    private LabelRepository labelRepository;
    public LabelService(LabelRepository labelRepository){
        this.labelRepository = labelRepository;
    }

    public Label read(Integer id){
        return labelRepository.getById(id);
    }

    public List<Label> readAll(){
        return labelRepository.getAll();
    }

    public Label create(Label label){
        return labelRepository.save(label);
    }

    public Label update(Label label){
        labelRepository.update(label);
        return label;
    }
    public void delete(Integer id){
        labelRepository.deleteById(id);
    }
}
