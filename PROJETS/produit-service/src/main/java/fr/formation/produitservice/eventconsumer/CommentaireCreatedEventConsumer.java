package fr.formation.produitservice.eventconsumer;

import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import fr.formation.produitservice.command.AcceptCommentaireCommand;
import fr.formation.produitservice.command.RejectCommentaireCommand;
import fr.formation.produitservice.event.CommentaireCreatedEvent;
import fr.formation.produitservice.model.Produit;
import fr.formation.produitservice.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component("onCommentaireCreated")
@RequiredArgsConstructor
@Log4j2
public class CommentaireCreatedEventConsumer implements Consumer<CommentaireCreatedEvent> {
    private final ProduitRepository repository;
    private final StreamBridge streamBridge;

    @Override
    public void accept(CommentaireCreatedEvent evt) {
        log.debug("Commentaire {} created for produit {} ...", evt.getCommentaireId(), evt.getProduitId());

        Optional<Produit> optProduit = this.repository.findById(evt.getProduitId());

        if (optProduit.isPresent() && optProduit.get().isNotable()) {
            log.debug("Commentaire {} accepted!", evt.getCommentaireId());
            
            this.streamBridge.send("commentaire.accepted", AcceptCommentaireCommand.builder()
            .commentaireId(evt.getCommentaireId())
            .produitId(evt.getProduitId())
            .build()
            );
        }
        
        else {
            log.debug("Commentaire {} rejected!", evt.getCommentaireId());
            
            this.streamBridge.send("commentaire.rejected", RejectCommentaireCommand.builder()
                .commentaireId(evt.getCommentaireId())
                .produitId(evt.getProduitId())
                .build()
            );
        }
    }
}
