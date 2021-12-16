package com.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "sastojak")
@Getter
@Setter
public class GenericIngredient {

    public GenericIngredient(int id, String name, Category category, AmountType amountType) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.amountType = amountType;
    }

    @Id
    @Column(name = "id_sastojak")
    @GeneratedValue
    private int id;
    @Column(name = "ime")
    private String name;
    @ManyToOne
    @JoinColumn(name = "id_kategorija")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "id_kolicina")
    private AmountType amountType;

    @OneToMany(mappedBy = "genericIngredient")
    @JsonIgnore
    private Set<Ingredient> ingredientSet;

    public GenericIngredient() {

    }
}
