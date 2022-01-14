package com.example.controller;

import com.example.model.GenericIngredient;
import com.example.service.GenericIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenericIngredientController {

    @Autowired
    private GenericIngredientService service;

    @GetMapping(value = "/getAllGenericIngredients")
    public List<GenericIngredient> getAll(){
        return service.getAll();
    }

    @GetMapping(value = "/getGenericIngredient")
    public GenericIngredient getOne(@RequestParam int id){
        return service.getOne(id);
    }

}
