package com.example.mealplanner.model.util;

import com.example.mealplanner.model.GenericIngredient;

import java.time.LocalDate;

public class IngredientConfirmItem {

    private GenericIngredient ingredient;
    private LocalDate date;
    private Double amount;

    public IngredientConfirmItem(GenericIngredient ingredient, LocalDate date, Double amount) {
        this.ingredient = ingredient;
        this.date = date;
        this.amount = amount;
    }

    public IngredientConfirmItem(){

    }

    public IngredientConfirmItem(IngredientConfirmItem item){
        this.ingredient = item.getIngredient();
        this.date = item.getDate();
        this.amount = item.getAmount();
    }

    public GenericIngredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(GenericIngredient ingredient) {
        this.ingredient = ingredient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
