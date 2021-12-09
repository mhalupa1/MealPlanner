package com.example.controller;

import com.example.model.Ingredient;
import com.example.service.IngredientService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IngredientController {

    @Autowired
    private IngredientService service;

    @GetMapping(value = "/getAllIngredients")
    public List<Ingredient> getAll(){
        return service.getAll();
    }

    @GetMapping(value = "/getIngredient")
    public Ingredient getOne(@RequestParam int id){
        return service.getOne(id);
    }

    @PostMapping(value = "/saveIngredient")
    public Ingredient save(@RequestBody Ingredient ingredient){
        return service.save(ingredient);
    }
}
