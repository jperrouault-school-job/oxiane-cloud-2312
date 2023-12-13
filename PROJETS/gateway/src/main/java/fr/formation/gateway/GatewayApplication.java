package fr.formation.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder, CustomFilter customFilter) {
        return builder.routes()
            // Vers Query Service
            .route(r ->
                r   .method(HttpMethod.GET)
                    // .filters(f -> f.filter(customFilter))
                    .uri("lb://query-service")
            )

            // Vers Produit Service
            .route(r ->
                r   .path("/api/produit/**")
                    .uri("lb://produit-service")
            )

            // Vers Commentaire Service
            .route(r ->
                r   .path("/api/commentaire/**")
                    .uri("lb://commentaire-service")
            )

            .build()
        ;
    }
}
