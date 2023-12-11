package fr.formation.produitservice.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Commentaire {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "text")
    private String texte;
    
    private int note;

    @JoinColumn(name = "produit_id")
    @ManyToOne
    private Produit produit;
}
