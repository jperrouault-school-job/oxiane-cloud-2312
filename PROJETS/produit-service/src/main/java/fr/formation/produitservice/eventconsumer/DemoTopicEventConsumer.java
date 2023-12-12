package fr.formation.produitservice.eventconsumer;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component("onDemoTopic")
@Log4j2
public class DemoTopicEventConsumer implements Consumer<String> {
    @Override
    public void accept(String evt) {
        log.debug("Message reçu (component) : {}", evt);
    }
}
