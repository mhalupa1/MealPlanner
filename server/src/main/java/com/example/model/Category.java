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
    private int id;
    @Column(name = "ime")
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<GenericIngredient> ingredientSet;

    public Category() {

    }
}
