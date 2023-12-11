package fr.formation.commentaireservice.api;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.commentaireservice.model.Commentaire;
import fr.formation.commentaireservice.model.Produit;
import fr.formation.commentaireservice.repository.CommentaireRepository;
import fr.formation.commentaireservice.repository.ProduitRepository;
import fr.formation.commentaireservice.request.CreateCommentaireRequest;
import fr.formation.commentaireservice.response.CommentaireResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/commentaire")
@RequiredArgsConstructor
public class CommentaireApiController {
    private final CommentaireRepository repository;
    private final ProduitRepository produitRepository;

    @GetMapping("/{id}")
    public CommentaireResponse findById(@PathVariable String id) {
        Commentaire commentaire = this.repository.findById(id).orElseThrow();
        CommentaireResponse resp = new CommentaireResponse();

        BeanUtils.copyProperties(commentaire, resp);
        resp.setProduitId(commentaire.getProduit().getId());
        resp.setProduitNom(commentaire.getProduit().getNom());

        return resp;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody CreateCommentaireRequest request) {
        Optional<Produit> optProduit = this.produitRepository.findById(request.getProduitId());

        if (optProduit.isEmpty() || !optProduit.get().isNotable()) {
            throw new RuntimeException();
        }
        
        Commentaire commentaire = new Commentaire();

        BeanUtils.copyProperties(request, commentaire);
        commentaire.setProduit(optProduit.get());

        this.repository.save(commentaire);

        return commentaire.getId();
    }
}
