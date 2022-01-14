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

    public PantryIngredient save(PantryIngredient pantryIngredient){
        return repo.save(pantryIngredient);
    }

    public List<PantryIngredient> saveAll(List<PantryIngredient> ingredients){
        return repo.saveAll(ingredients);
    }

    public PantryIngredient update(int id, PantryIngredient pantryIngredient){
        PantryIngredient pantryIng = repo.getById(id);
        if (pantryIng != null){
            pantryIng.setAmount(pantryIngredient.getAmount());
            pantryIng.setExpirationDate(pantryIngredient.getExpirationDate());
            pantryIng.setIngredient(pantryIngredient.getIngredient());
            pantryIng.setPantry(pantryIngredient.getPantry());
            return repo.save(pantryIng);
        }
        return pantryIng;

    }

    public boolean delete(int id){
        if(repo.existsById(id)){
            repo.deleteById(id);
            return true;
        }else{
            return false;
        }  
    }
}
