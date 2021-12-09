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
@Entity(name = "tip_kolicine")
public class AmountType {

    @Id
    @Column(name = "id_kolicina")
    private int id;
    @Column(name = "tip")
    private String type;
    @Column(name = "default_kolicina")
    private BigDecimal defaultAmount;

    @OneToMany(mappedBy = "amountType")
    @JsonIgnore
    private Set<GenericIngredient> ingredientSet;

}
