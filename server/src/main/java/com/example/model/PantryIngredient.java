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

    @Embeddable
    class PantryIngredientKey implements Serializable {
        @Column(name = "id_smocnica")
        private int pantryId;

        @Column(name = "id_proizvod")
        private int ingredientId;
    }

    @EmbeddedId
    private PantryIngredientKey id;

    @ManyToOne
    @MapsId("pantryId")
    @JoinColumn(name = "id_smocnica")
    private Pantry pantry;


    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "id_proizvod")
    private Ingredient ingredient;

    @Column(name = "rok_trajanja")
    private LocalDate expirationDate;

    @Column(name = "kolicina")
    private BigDecimal amount;

    public PantryIngredient() {

    }


    public PantryIngredientKey getId() {
        return this.id;
    }

    public void setId(PantryIngredientKey id) {
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
