package com.example.dao;

import java.util.List;

import com.example.model.Pantry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PantryDao extends JpaRepository<Pantry,Integer> {

    @Query("SELECT s from smocnica s LEFT OUTER JOIN s.user where s.user.id = :id")
    public List<Pantry> getForUser(@Param("id") int id);
}
