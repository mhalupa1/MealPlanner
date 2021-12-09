package com.example.controller;

import com.example.model.Category;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryService service;

    @GetMapping(value = "/getCategories")
    public List<Category> getAll(){
        return service.getAll();
    }

    @GetMapping(value = "/getCategory")
    public Category getOne(@RequestParam int id){
        return service.getOne(id);
    }
}
