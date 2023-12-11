package fr.formation.produitservice.model;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Produit {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "name")
    private String nom;

    @Column(name = "price")
    private BigDecimal prix;

    private boolean notable;

    @OneToMany(mappedBy = "produit")
    private List<Commentaire> commentaires;
}
