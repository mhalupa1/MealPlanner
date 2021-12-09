package com.example.service;

import com.example.dao.IngredientDao;
import com.example.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    IngredientDao repo;

    public List<Ingredient> getAll(){
        return repo.getAll();
    }

    public Ingredient getOne(int id){
        return repo.getById(id);
    }

    public Ingredient save(Ingredient ingredient){
        return repo.save(ingredient);
    }
}
