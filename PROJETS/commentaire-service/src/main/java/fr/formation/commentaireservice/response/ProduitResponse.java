package fr.formation.commentaireservice.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProduitResponse {
    private String id;
    private String nom;
    private boolean notable;
}
