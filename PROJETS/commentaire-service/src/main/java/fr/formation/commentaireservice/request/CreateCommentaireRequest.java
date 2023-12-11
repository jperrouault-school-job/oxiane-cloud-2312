package fr.formation.commentaireservice.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCommentaireRequest {
    private String texte;
    private int note;
    private String produitId;
}
