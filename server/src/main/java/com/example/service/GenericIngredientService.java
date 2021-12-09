package com.example.service;

import com.example.dao.GenericIngredientDao;
import com.example.model.GenericIngredient;
import com.example.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericIngredientService {

    @Autowired
    GenericIngredientDao repo;

    public List<GenericIngredient> getAll(){
        return repo.getAll();
    }

    public GenericIngredient getOne(int id){
        return repo.getById(id);
    }


}
