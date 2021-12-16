package com.example.dao;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    @Query(value = "SELECT * from korisnik where korisnicko_ime=:username", nativeQuery = true)
    public User getForUsername(String username);
}
