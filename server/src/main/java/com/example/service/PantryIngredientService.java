package com.example.service;

import com.example.dao.PantryIngredientDao;
import com.example.model.PantryIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PantryIngredientService {

    @Autowired
    PantryIngredientDao repo;


    public List<PantryIngredient> getPantryIngredients(int pantryId){
        return repo.getForPantry(pantryId);
    }
}
