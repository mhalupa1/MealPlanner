package com.example.service;

import com.example.dao.CategoryDao;
import com.example.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao repo;

    public List<Category> getAll(){
        return repo.findAll();
    }

    @GetMapping(value = "/getCategory")
    public Category getOne(@RequestParam int id){
        return repo.getById(id);
    }
}
