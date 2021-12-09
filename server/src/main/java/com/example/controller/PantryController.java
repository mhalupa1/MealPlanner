package com.example.controller;

import com.example.model.Pantry;
import com.example.service.PantryService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PantryController {
    @Autowired
    private PantryService service;

    @GetMapping(value = "/getUserPantry")
    public List<Pantry> getUserPantry(@RequestParam int userId){
        return service.getUserPantry(userId);
    }

}
