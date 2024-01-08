package com.iam_user_management.keycloak.client;

import com.iam_user_management.keycloak.config.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakAdminClient {

    private final KeycloakProperties keycloakProperties;

    @Bean
    public Keycloak getKeycloakAdminClient() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.getClientServerUrl())
                .realm(keycloakProperties.getClientRealm())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(keycloakProperties.getMasterClientId())
                .clientSecret(keycloakProperties.getMasterClientSecret())
                .build();
    }

}
