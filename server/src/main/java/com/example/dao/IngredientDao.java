package com.example.dao;

import com.example.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientDao extends JpaRepository<Ingredient,Integer> {

    @Query("SELECT n FROM proizvod n LEFT JOIN FETCH n.genericIngredient")
    public List<Ingredient> getAll();

    @Query("SELECT n FROM proizvod n LEFT JOIN FETCH n.genericIngredient WHERE n.barcode=:barcode")
    public Ingredient getByBarcode(@Param("barcode") String barcode);

    @Query("SELECT n from proizvod n LEFT JOIN FETCH n.genericIngredient where n.genericIngredient.id in (:ingredients) AND n.barcode IS NULL")
    public List<Ingredient> getByGenericIds(@Param("ingredients") List<Integer> ingredients);
}
