package com.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "namirnica_smocnice")
@Getter
@Setter
public class PantryIngredient {
    @Id
    @GeneratedValue
    @Column(name = "id_namirnica_smocnice")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_smocnica")
    private Pantry pantry;


    @ManyToOne
    @JoinColumn(name = "id_proizvod")
    private Ingredient ingredient;

    @Column(name = "rok_trajanja")
    private LocalDate expirationDate;

    @Column(name = "kolicina")
    private BigDecimal amount;


    public PantryIngredient(int id, Pantry pantry, Ingredient ingredient, LocalDate expirationDate, BigDecimal amount) {
        this.id = id;
        this.pantry = pantry;
        this.ingredient = ingredient;
        this.expirationDate = expirationDate;
        this.amount = amount;
    }


    public PantryIngredient() {

    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public Pantry getPantry() {
        return this.pantry;
    }

    public void setPantry(Pantry pantry) {
        this.pantry = pantry;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
