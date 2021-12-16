package com.example.service;

import com.example.dao.UserDao;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao repo;

    public User getOne(int id){
        return repo.getById(id);
    }

    public User signup(String username, String password){
        User dbUser = repo.getForUsername(username);
        if(dbUser != null){
            return null;
        }
        User user = new User(username,password);
        return repo.save(user);
    }

    public User login(String username, String password){
        User dbUser = repo.login(username,password);
        if(dbUser == null){
            return null;
        }
        return new User(username,password);
    }
}
