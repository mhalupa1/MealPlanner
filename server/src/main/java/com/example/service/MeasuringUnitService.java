package com.example.service;

import com.example.dao.MeasuringUnitDao;
import com.example.model.MeasuringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class MeasuringUnitService {

    @Autowired
    MeasuringUnitDao repo;


    public List<MeasuringUnit> getAll(){
        return repo.findAll();
    }


    public MeasuringUnit getOne(@RequestParam int id){
        return repo.getById(id);
    }
}




