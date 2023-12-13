package fr.formation.commentaireservice.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class CqrsCreateCommentaireCommand {
    private String id;
    private String texte;
    private int noteQualite;
    private int noteQualitePrix;
    private int noteFacilite;
    private String produitId;
}
