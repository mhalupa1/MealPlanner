package com.example.controller;

import com.example.model.PantryIngredient;
import com.example.service.PantryIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PantryIngredientController {
    @Autowired
    private PantryIngredientService service;

    @GetMapping(value = "/getPantryIngredients")
    public List<PantryIngredient> getPantryIngredients(@RequestParam int id){
        return service.getPantryIngredients(id);
    }
    @PostMapping(value = "/savePantryIngredient")
    public PantryIngredient savePantryIngredient(@RequestBody PantryIngredient pantryIngredient){
        return service.save(pantryIngredient);
    }
    @PutMapping(value = "/updatePantryIngredient")
    public ResponseEntity updatePantryIngredient(@RequestParam int id, 
    @RequestBody PantryIngredient pantryIngredient){
        PantryIngredient pantryIng = service.update(id,pantryIngredient);
        if (pantryIngredient != null){
            return ResponseEntity.status(HttpStatus.OK).body("Updated succesfully");
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Pantry ingredient doesn't exist");
        }
    }

    @DeleteMapping(value = "/deletePantryIngredient")
    public ResponseEntity delete(@RequestParam int id){
        if(service.delete(id)){
            return ResponseEntity.ok("Item deleted");
        }else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Couldn't find requested item");
        }
    }

}
