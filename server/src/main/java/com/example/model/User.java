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

    int id;
    @Column(name = "korisnicko_ime")
    String username;
    @Column(name = "sifra")
    String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Pantry> pantry;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {

    }
}
