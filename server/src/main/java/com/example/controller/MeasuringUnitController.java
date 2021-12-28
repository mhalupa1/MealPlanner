package com.example.controller;

import com.example.model.MeasuringUnit;
import com.example.service.MeasuringUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MeasuringUnitController {

    @Autowired
    MeasuringUnitService service;

    @GetMapping(value = "/getMeasuringUnits")
    public List<MeasuringUnit> getAll(){
        return service.getAll();
    }

    @GetMapping(value = "/getMeasuringUnit")
    public MeasuringUnit getOne(@RequestParam int id){
        return service.getOne(id);
    }
}
