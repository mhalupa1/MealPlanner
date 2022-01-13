package com.example.controller;

import java.util.List;

import com.example.model.Pantry;
import com.example.service.PantryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PantryController {
    @Autowired
    private PantryService service;
    
    @GetMapping(value = "/getUserPantries")
    public List<Pantry> getUserPantries(@RequestParam int id){
        return service.getUserPantries(id);
    }

    @PostMapping(value = "/savePantry")
    public Pantry save(@RequestBody Pantry pantry){
        return service.save(pantry);
    }
    @DeleteMapping(value = "/deletePantry")
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
