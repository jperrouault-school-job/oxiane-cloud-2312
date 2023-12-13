package fr.formation.produitservice.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorization -> {
            // authorization.requestMatchers("/**").permitAll();
            // authorization.requestMatchers("/**").authenticated();
            authorization.requestMatchers("/**").hasRole("ADMIN");
        });

        // Validation du Jeton JWT (sans role mapping)
        // http.oauth2ResourceServer(server -> server.jwt(Customizer.withDefaults()));
        
        // Validation du Jeton JWT (avec role mapping)
        http.oauth2ResourceServer(server -> 
            server.jwt(jwt ->
                jwt.jwtAuthenticationConverter(this.jwtAuthenticationConverter())
            )
        );

        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(this.jwtConverter());

        return converter;
    }

    private Converter<Jwt, Collection<GrantedAuthority>> jwtConverter() {
        return jwtToken -> {
            Map<String, Object> realmAccess = jwtToken.getClaim("realm_access");
            List<String> roles = (List<String>)realmAccess.get("roles");
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
    
            return authorities;
        };
    }
}
