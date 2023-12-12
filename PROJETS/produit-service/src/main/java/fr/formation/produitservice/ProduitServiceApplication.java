package fr.formation.produitservice;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableFeignClients
@Log4j2
public class ProduitServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProduitServiceApplication.class, args);
    }

    // @Bean
    Consumer<String> onDemoTopic() {
        return evt -> {
            log.debug("Message reçu : {}", evt);
        };
    }

    @Bean
    Function<String, String> onDemoTopicFunction() {
        return evt -> {
            log.debug("Message reçu (function) : {}", evt);
            return evt.toUpperCase();
        }; 
    }
}
