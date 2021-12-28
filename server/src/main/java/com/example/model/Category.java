package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "kategorija")
@Getter
@Setter
public class Category {

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(name = "id_kategorija")
    @GeneratedValue
    private int id;
    @Column(name = "naziv")
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<GenericIngredient> ingredientSet;

    public Category() {

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

    public Set<GenericIngredient> getIngredientSet() {
        return this.ingredientSet;
    }

    public void setIngredientSet(Set<GenericIngredient> ingredientSet) {
        this.ingredientSet = ingredientSet;
    }

}
