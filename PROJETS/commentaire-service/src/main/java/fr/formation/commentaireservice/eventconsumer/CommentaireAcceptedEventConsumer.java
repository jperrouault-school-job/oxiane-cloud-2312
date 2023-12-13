package fr.formation.commentaireservice.eventconsumer;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import fr.formation.commentaireservice.command.CqrsCreateCommentaireCommand;
import fr.formation.commentaireservice.event.CommentaireAcceptedEvent;
import fr.formation.commentaireservice.model.Commentaire;
import fr.formation.commentaireservice.model.Commentaire.Etat;
import fr.formation.commentaireservice.repository.CommentaireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component("onCommentaireAccepted")
@RequiredArgsConstructor
@Log4j2
public class CommentaireAcceptedEventConsumer implements Function<CommentaireAcceptedEvent, CqrsCreateCommentaireCommand> {
    private final CommentaireRepository repository;
    
    @Override
    public CqrsCreateCommentaireCommand apply(CommentaireAcceptedEvent evt) {
        log.debug("Commentaire {} accepted!", evt.getCommentaireId());

        Optional<Commentaire> optCommentaire = this.repository.findById(evt.getCommentaireId());

        if (optCommentaire.isEmpty()) {
            return CqrsCreateCommentaireCommand.builder().build();
        }

        Commentaire commentaire = optCommentaire.get();

        commentaire.setEtat(Etat.OK);

        this.repository.save(commentaire);

        return CqrsCreateCommentaireCommand.builder()
            .id(commentaire.getId())
            .texte(commentaire.getTexte())
            .noteQualite(commentaire.getNoteQualite())
            .noteQualitePrix(commentaire.getNoteQualitePrix())
            .noteFacilite(commentaire.getNoteFacilite())
            .produitId(commentaire.getProduitId())
            .build()
        ;
    }
}
