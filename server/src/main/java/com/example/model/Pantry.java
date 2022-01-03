package com.example.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.type.TrueFalseType;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "smocnica")
@Getter
@Setter
public class Pantry {
    @Id
    @Column(name = "id_smocnica")
    @GeneratedValue
    private int id;
    @Column(name = "naziv")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_korisnik")
    private User user;

    @OneToMany(mappedBy = "pantry", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<PantryIngredient> pantryIngredientSet;

    public Pantry(int id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }


    public Pantry() {
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<PantryIngredient> getPantryIngredientSet() {
        return this.pantryIngredientSet;
    }

    public void setPantryIngredientSet(Set<PantryIngredient> pantryIngredientSet) {
        this.pantryIngredientSet = pantryIngredientSet;
    }


    
}
