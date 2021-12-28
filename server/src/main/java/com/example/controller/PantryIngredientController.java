package com.example.controller;

import com.example.model.PantryIngredient;
import com.example.service.PantryIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PantryIngredientController {
    @Autowired
    private PantryIngredientService service;

    @GetMapping(value = "/getPantryIngredients")
    public List<PantryIngredient> getPantryIngredients(@RequestParam int pantryId){
        return service.getPantryIngredients(pantryId);
    }

}
