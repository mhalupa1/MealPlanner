package com.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "smocnica")
@Getter
@Setter
public class Pantry {

    @Embeddable
    class PantryKey implements Serializable {
        @Column(name = "id_korisnik")
        private int userId;

        @Column(name = "id_namirnica")
        private int ingredientId;
    }

    @EmbeddedId
    private PantryKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "id_korisnik")
    private User user;


    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "id_namirnica")
    private Ingredient ingredient;

    @Column(name = "rok_trajanja")
    private LocalDate expirationDate;

    @Column(name = "kolicina")
    private BigDecimal amount;

    public Pantry() {

    }
}
