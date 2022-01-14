package com.example.controller;

import com.example.model.Ingredient;
import com.example.service.IngredientService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/getIngredientsByGenericId")
    public List<Ingredient> getManyByGenericId(@RequestBody List<Integer> ids){
        List<Ingredient> ing = service.getManyById(ids);
        return ing;
    }

    @GetMapping(value = "/getIngredientByBarcode")
    public ResponseEntity getByBarcode(@RequestParam String barcode){
        Ingredient ingredient = service.getByBarcode(barcode);
        if (ingredient != null){
            return ResponseEntity.ok(ingredient);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Barcode not found");
        }
    }

}
