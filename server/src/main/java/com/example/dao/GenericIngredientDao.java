package com.example.dao;

import com.example.model.GenericIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenericIngredientDao extends JpaRepository<GenericIngredient,Integer> {

    @Query("SELECT s FROM sastojak s left join FETCH s.category left join fetch s.measuringUnit")
    List<GenericIngredient> getAll();
}
