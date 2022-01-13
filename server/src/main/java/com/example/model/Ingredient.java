package com.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "proizvod")
@Getter
@Setter
public class Ingredient {

    @Id
    @Column(name = "id_proizvod")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "naziv")
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
    private Set<PantryIngredient> pantryIngredientSet;


    public Ingredient(int id, String name, BigDecimal amount, String barcode, GenericIngredient genericIngredient) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.barcode = barcode;
        this.genericIngredient = genericIngredient;
    }

    public Ingredient() {

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

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public GenericIngredient getGenericIngredient() {
        return this.genericIngredient;
    }

    public void setGenericIngredient(GenericIngredient genericIngredient) {
        this.genericIngredient = genericIngredient;
    }

    public Set<PantryIngredient> getPantryIngredientSet() {
        return this.pantryIngredientSet;
    }

    public void setPantryIngredientSet(Set<PantryIngredient> pantryIngredientSet) {
        this.pantryIngredientSet = pantryIngredientSet;
    }

}
