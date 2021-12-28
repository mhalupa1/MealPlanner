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

    public GenericIngredient(int id, String name, Category category, MeasuringUnit measuringUnit) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.measuringUnit = measuringUnit;
    }

    @Id
    @Column(name = "id_sastojak")
    @GeneratedValue
    private int id;
    @Column(name = "naziv")
    private String name;
    @ManyToOne
    @JoinColumn(name = "id_kategorija")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "id_mjerna_jedinica")
    private MeasuringUnit measuringUnit;

    @OneToMany(mappedBy = "genericIngredient")
    @JsonIgnore
    private Set<Ingredient> ingredientSet;

    public GenericIngredient() {

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

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public MeasuringUnit getMeasuringUnit() {
        return this.measuringUnit;
    }

    public void setMeasuringUnit(MeasuringUnit measuringUnit) {
        this.measuringUnit = measuringUnit;
    }

    public Set<Ingredient> getIngredientSet() {
        return this.ingredientSet;
    }

    public void setIngredientSet(Set<Ingredient> ingredientSet) {
        this.ingredientSet = ingredientSet;
    }

}
