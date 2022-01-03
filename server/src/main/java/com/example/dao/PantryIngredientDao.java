package com.example.dao;

import com.example.model.PantryIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PantryIngredientDao extends JpaRepository<PantryIngredient,Integer> {

    @Query("SELECT s from namirnica_smocnice s LEFT OUTER JOIN s.pantry LEFT OUTER JOIN s.ingredient where s.pantry.id = :id")
    public List<PantryIngredient> getForPantry(@Param("id") int id);
}
