package com.ruslan.crudapp.controller;

import com.ruslan.crudapp.model.Writer;
import com.ruslan.crudapp.repository.WriterRepository;
import com.ruslan.crudapp.service.WriterService;

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
    public Writer create(Writer writer){
        writerService.create(writer);
        return writer;
    }
    public Writer update(Writer writer){
        writerService.update(writer);
        return writer;
    }
    public void delete(Integer id){
        writerService.delete(id);
    }
}
