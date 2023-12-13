package fr.formation.produitservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorization -> {
            // authorization.requestMatchers("/**").permitAll();
            authorization.requestMatchers("/**").authenticated();
        });

        http.oauth2ResourceServer(server -> server.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
