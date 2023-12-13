package fr.formation.queryservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Commentaire {
    @Id
    private String id;

    @Column(name = "text")
    private String texte;
    
    private int noteQualite;
    private int noteQualitePrix;
    private int noteFacilite;

    @ManyToOne
    private Produit produit;
}
