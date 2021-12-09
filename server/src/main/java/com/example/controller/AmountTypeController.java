package com.example.controller;

import com.example.model.AmountType;
import com.example.service.AmountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AmountTypeController {

    @Autowired
    AmountTypeService service;

    @GetMapping(value = "/getAmountTypes")
    public List<AmountType> getAll(){
        return service.getAll();
    }

    @GetMapping(value = "/getAmountType")
    public AmountType getOne(@RequestParam int id){
        return service.getOne(id);
    }
}
