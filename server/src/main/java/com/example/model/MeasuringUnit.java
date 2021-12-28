package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity(name = "mjerna_jedinica")
public class MeasuringUnit {

    @Id
    @Column(name = "id_mjerna_jedinica")
    @GeneratedValue
    private int id;
    @Column(name = "naziv")
    private String name;
    @Column(name = "default_kolicina")
    private BigDecimal defaultAmount;

    @OneToMany(mappedBy = "measuringUnit")
    @JsonIgnore
    private Set<GenericIngredient> ingredientSet;


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

    public BigDecimal getDefaultAmount() {
        return this.defaultAmount;
    }

    public void setDefaultAmount(BigDecimal defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    public Set<GenericIngredient> getIngredientSet() {
        return this.ingredientSet;
    }

    public void setIngredientSet(Set<GenericIngredient> ingredientSet) {
        this.ingredientSet = ingredientSet;
    }


}
