package com.wcc.config.auth;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 * Configuration class for integrating Keycloak with Spring Security.
 * <p>
 * This class sets up the necessary authentication provider, session management strategy,
 * and HTTP security configuration required for Keycloak-based authentication and authorization.
 * </p>
 * <p>
 * It enables global method-level security using annotations like {@code @PreAuthorize}, {@code @Secured}, etc.
 * </p>
 * @author CY 
 */

@KeycloakConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class KeycloakConfig extends KeycloakWebSecurityConfigurerAdapter {

    /**
     * Configures the global authentication manager to use Keycloak as an authentication provider.
     * Applies a {@link SimpleAuthorityMapper} to ensure all roles are prefixed with "ROLE_".
     *
     * @param auth the {@link AuthenticationManagerBuilder} used to build the authentication manager
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
        grantedAuthorityMapper.setPrefix("ROLE_");
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    /**
     * Defines the session authentication strategy.
     * Since tokens are used for authentication, this returns a {@link NullAuthenticatedSessionStrategy}.
     *
     * @return a stateless session strategy
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        //The app use tokens so sessions are not necessary
        return new NullAuthenticatedSessionStrategy();
    }

    /**
     * Provides the {@link HttpSessionManager} bean required by Keycloak.
     * Ensures a single instance is registered if not already present.
     *
     * @return an instance of {@link HttpSessionManager}
     */
    @Bean
    @Override
    @ConditionalOnMissingBean(HttpSessionManager.class)
    protected HttpSessionManager httpSessionManager() {
        return new HttpSessionManager();
    }

    /**
     * Configures HTTP security for the application.
     * <ul>
     *     <li>Disables CSRF protection</li>
     *     <li>Enforces stateless session management</li>
     *     <li>Delegates to Keycloak's HTTP security configuration</li>
     * </ul>
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs during configuration
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable()
                .authorizeRequests()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}