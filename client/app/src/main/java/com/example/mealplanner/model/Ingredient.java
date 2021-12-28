package com.example.mealplanner.model;

import java.math.BigDecimal;

public class Ingredient {

    private int id;
    private String name;
    private BigDecimal amount;
    private String barcode;
    private GenericIngredient genericIngredient;

    public Ingredient(int id, String name, BigDecimal amount, String barcode, GenericIngredient genericIngredient) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.barcode = barcode;
        this.genericIngredient = genericIngredient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public GenericIngredient getGenericIngredient() {
        return genericIngredient;
    }

    public void setGenericIngredient(GenericIngredient genericIngredient) {
        this.genericIngredient = genericIngredient;
    }
}
