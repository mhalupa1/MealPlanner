package com.example.mealplanner.model;

public class AmountType {


    private int id;
    private String type;
    private Double defaultAmount;


    public AmountType(int id, String type, Double defaultAmount) {
        this.id = id;
        this.type = type;
        this.defaultAmount = defaultAmount;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(Double defaultAmount) {
        this.defaultAmount = defaultAmount;
    }
}
