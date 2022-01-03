package com.example.service;

import java.util.List;

import com.example.dao.PantryDao;
import com.example.model.Pantry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PantryService {
    @Autowired
    PantryDao repo;

    public List<Pantry> getUserPantries(int userId){
        return repo.getForUser(userId);
    }

    public Pantry save(Pantry pantry){
        return repo.save(pantry);
    }

    public void delete(int id){
        repo.deleteById(id);
    }
}
