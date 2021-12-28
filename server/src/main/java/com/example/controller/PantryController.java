package com.example.controller;

import java.util.List;

import com.example.model.Pantry;
import com.example.service.PantryService;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Pantry> getUserPantries(@RequestParam int userId){
        return service.getUserPantries(userId);
    }

    @PostMapping(value = "/savePantry")
    public Pantry save(@RequestBody Pantry pantry){
        return service.save(pantry);
    }
}
