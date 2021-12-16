package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(value = "/getUser")
    public User getOne(@RequestParam int id){
        return service.getOne(id);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity signup(@RequestParam String username, @RequestParam String password){
        User user = service.signup(username,password);
        if (user != null){
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("This username already exists");
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password){
        //TODO: map entity id
        User user = service.login(username,password);
        if (user != null){
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Wrong username or password");
        }
    }
}

