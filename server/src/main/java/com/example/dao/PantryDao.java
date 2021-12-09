package com.example.dao;

import com.example.model.Pantry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PantryDao extends JpaRepository<Pantry,Integer> {

    @Query("SELECT s from smocnica s LEFT OUTER JOIN s.user LEFT OUTER JOIN s.ingredient where s.user.id = :id")
    public List<Pantry> getForUser(int id);
}
