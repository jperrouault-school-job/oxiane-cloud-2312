package fr.formation.queryservice.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Produit {
    @Id
    private String id;

    @Column(name = "name")
    private String nom;

    @Column(name = "price")
    private BigDecimal prix;

    private int note;
}
