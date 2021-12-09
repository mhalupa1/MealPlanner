package com.example.service;

import com.example.dao.AmountTypeDao;
import com.example.model.AmountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class AmountTypeService {

    @Autowired
    AmountTypeDao repo;


    public List<AmountType> getAll(){
        return repo.findAll();
    }


    public AmountType getOne(@RequestParam int id){
        return repo.getById(id);
    }
}




