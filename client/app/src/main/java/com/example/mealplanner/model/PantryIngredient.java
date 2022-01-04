package com.example.mealplanner.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PantryIngredient {

    private int id;
    private LocalDate expirationDate;
    private BigDecimal amount;
    private Pantry pantry;
    private Ingredient ingredient;


    public PantryIngredient(int id, LocalDate expirationDate, BigDecimal amount, Pantry pantry, Ingredient ingredient) {
        this.id = id;
        this.expirationDate = expirationDate;
        this.amount = amount;
        this.pantry = pantry;
        this.ingredient = ingredient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Pantry getPantry() {
        return pantry;
    }

    public void setPantry(Pantry pantry) {
        this.pantry = pantry;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
