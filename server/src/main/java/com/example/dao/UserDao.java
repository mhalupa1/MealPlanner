package com.example.dao;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    @Query(value = "SELECT k from korisnik k where k.username=:username")
    public User getForUsername(@Param("username") String username);

    @Query(value = "SELECT k from korisnik k where k.username=:username AND k.password=:password")
    public User login(@Param("username") String username, @Param("password") String password);
}
