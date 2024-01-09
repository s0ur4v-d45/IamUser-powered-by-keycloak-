package com.iam_user_management.keycloak.service;

import com.iam_user_management.keycloak.config.KeycloakProperties;
import com.iam_user_management.keycloak.model.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService{

    private final Keycloak keycloakClient;
    private final KeycloakProperties keycloakProperties;
    @Override
    public AccessTokenResponse generateToken(final TokenDTO tokenDTO) {

    Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(keycloakProperties.getClientServerUrl())
                    .grantType(OAuth2Constants.PASSWORD)
                    .realm(tokenDTO.getRealmName())
                    .clientId(keycloakProperties.getOrgClientId())
                    .clientSecret(keycloakProperties.getOrgClientSecret())

                    .username(tokenDTO.getUsername())
                    .password(tokenDTO.getPassword())
                    .build();
        return keycloak.tokenManager().getAccessToken();
    }
}
