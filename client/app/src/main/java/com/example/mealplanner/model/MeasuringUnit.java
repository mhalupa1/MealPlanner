package com.example.mealplanner.model;

public class MeasuringUnit {


    private int id;
    private String name;
    private Double defaultAmount;


    public MeasuringUnit(int id, String name, Double defaultAmount) {
        this.id = id;
        this.name = name;
        this.defaultAmount = defaultAmount;
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

    public Double getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(Double defaultAmount) {
        this.defaultAmount = defaultAmount;
    }
}
