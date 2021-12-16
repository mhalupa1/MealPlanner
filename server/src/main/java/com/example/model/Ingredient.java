package com.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "namirnica")
@Getter
@Setter
public class Ingredient {

    @Id
    @Column(name = "id_namirnica")
    @GeneratedValue
    private int id;
    @Column(name = "ime")
    private String name;
    @Column(name = "kolicina")
    private BigDecimal amount;
    @Column(name = "barkod")
    private String barcode;

    @ManyToOne
    @JoinColumn(name = "id_sastojak")
    private GenericIngredient genericIngredient;

    @OneToMany(mappedBy = "ingredient")
    @JsonIgnore
    private Set<Pantry> pantrySet;


    public Ingredient(int id, String name, BigDecimal amount, String barcode, GenericIngredient genericIngredient) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.barcode = barcode;
        this.genericIngredient = genericIngredient;
    }

    public Ingredient() {

    }
}
