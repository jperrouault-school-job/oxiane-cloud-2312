package fr.formation.commentaireservice.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
    
    private int noteQualite;
    private int noteQualitePrix;
    private int noteFacilite;

    @Enumerated(EnumType.STRING)
    private Etat etat;

    // @JoinColumn(name = "produit_id")
    // @ManyToOne
    // private Produit produit;

    @Column(name = "produit_id")
    private String produitId;

    public enum Etat {
        ATTENTE, OK;
    }
}
