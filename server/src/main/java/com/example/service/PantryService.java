package com.example.service;

import com.example.dao.PantryDao;
import com.example.model.Pantry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PantryService {

    @Autowired
    PantryDao repo;


    public List<Pantry> getUserPantry(int userId){
        return repo.getForUser(userId);
    }
}
