package fr.formation.commentaireservice.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentaireResponse {
    private String id;
    private String texte;
    private int note;
    private String produitId;
    private String produitNom;
}
