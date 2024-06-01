package com.ruslan.crudapp.service;

import com.ruslan.crudapp.model.Writer;
import com.ruslan.crudapp.repository.WriterRepository;

import java.util.List;

public class WriterService {
    private WriterRepository writerRepository;

    public WriterService(WriterRepository writerRepository){
        this.writerRepository = writerRepository;
    }

    public List<Writer> readAll(){
        return writerRepository.getAll();
    }

    public Writer read(Integer id){
        return writerRepository.getById(id);
    }
    public Writer create(Writer writer){
        writerRepository.save(writer);
        return writer;
    }
    public Writer update(Writer writer){
        writerRepository.update(writer);
        return writer;
    }
    public void delete(Integer id){
        writerRepository.deleteById(id);
    }
}
