package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "korisnik")
@Getter
@Setter
public class User {

    @Id
    @Column(name = "id_korisnik")
    @GeneratedValue
    private Integer id;
    @Column(name = "korisnicko_ime")
    private String username;
    @Column(name = "sifra")
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Pantry> pantry;


    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {

    }


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Pantry> getPantry() {
        return this.pantry;
    }

    public void setPantry(Set<Pantry> pantry) {
        this.pantry = pantry;
    }

}
