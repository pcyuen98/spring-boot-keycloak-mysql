package com.wcc.config.auth;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that tells Keycloak to use Spring Boot properties 
 * (application.yml or application.properties) instead of keycloak.json for configuration.
 *
 * <p>This enables centralized and consistent configuration of Keycloak using
 * Spring Boot's configuration management features.</p>
 * @author CY 
 */
@Configuration
public class KeyCloakResolver {
    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}