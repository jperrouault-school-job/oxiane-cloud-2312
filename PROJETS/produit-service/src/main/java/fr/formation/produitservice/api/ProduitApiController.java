package fr.formation.produitservice.api;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.produitservice.command.CqrsCreateProduitCommand;
import fr.formation.produitservice.feignclient.CommentaireFeignClient;
import fr.formation.produitservice.model.Produit;
import fr.formation.produitservice.repository.ProduitRepository;
import fr.formation.produitservice.request.CreateProduitRequest;
import fr.formation.produitservice.response.NativeProduitResponse;
import fr.formation.produitservice.response.ProduitResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produit")
@RequiredArgsConstructor
public class ProduitApiController {
    private final ProduitRepository repository;
    // private final CommentaireRepository commentaireRepository;
    private final CommentaireFeignClient commentaireFeignClient;
    private final StreamBridge streamBridge;

    @GetMapping
    public List<ProduitResponse> findAll() {
        return this.repository.findAll().stream()
            .map(p -> {
                // int note = (int)this.commentaireRepository.findAllByProduitId(p.getId())
                //     .stream()
                //     .mapToInt(Commentaire::getNote)
                //     .average()
                //     .orElse(-1);

                int note = this.commentaireFeignClient.getNoteByProduitId(p.getId());

                return ProduitResponse.builder()
                    .id(p.getId())
                    .nom(p.getNom())
                    .prix(p.getPrix())
                    .note(note)
                    .build()
                ;
            })
            .toList()
        ;
    }

    @GetMapping("/native/{id}")
    public NativeProduitResponse findNativeById(@PathVariable String id) {
        Produit produit = this.repository.findById(id).orElseThrow();
        NativeProduitResponse resp = new NativeProduitResponse();

        BeanUtils.copyProperties(produit, resp);

        return resp;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody CreateProduitRequest request) {
        Produit produit = new Produit();

        BeanUtils.copyProperties(request, produit);

        this.repository.save(produit);

        this.streamBridge.send("cqrs.produit.created", CqrsCreateProduitCommand.builder()
            .id(produit.getId())
            .nom(produit.getNom())
            .prix(produit.getPrix())
            .build()
        );

        return produit.getId();
    }
}
