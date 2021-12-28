package com.example.dao;

import com.example.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientDao extends JpaRepository<Ingredient,Integer> {

    @Query("SELECT n FROM proizvod n LEFT JOIN FETCH n.genericIngredient")
    public List<Ingredient> getAll();
}
