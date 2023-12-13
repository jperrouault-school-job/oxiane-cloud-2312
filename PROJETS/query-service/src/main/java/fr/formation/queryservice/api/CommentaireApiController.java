package fr.formation.queryservice.api;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.queryservice.repository.CommentaireRepository;
import fr.formation.queryservice.response.CommentaireResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/commentaire")
@RequiredArgsConstructor
public class CommentaireApiController {
    private final CommentaireRepository repository;

    @GetMapping("/{id}")
    public CommentaireResponse findById(@PathVariable String id) {
        fr.formation.queryservice.model.Commentaire commentaire = this.repository.findById(id).orElseThrow();
        CommentaireResponse resp = new CommentaireResponse();

        BeanUtils.copyProperties(commentaire, resp);
        resp.setProduitId(commentaire.getProduit().getId());
        resp.setProduitNom(commentaire.getProduit().getNom());

        return resp;
    }
}
