package com.iam_user_management.keycloak.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KeycloakProperties {

    @Value("${keycloak.client.server-url}")
    private String clientServerUrl;

    @Value("${keycloak.client.realm}")
    private String clientRealm;

    @Value("${keycloak.client.container-id}")
    private String containerId;

    @Value("${keycloak.client.org-client-id}")
    private String orgClientId;


    @Value("${keycloak.client.master-client-id}")
    private String masterClientId;


    @Value("${keycloak.client.master-client-secret}")
    private String masterClientSecret;

    @Value("${keycloak.client.org-client-secret}")
    private String orgClientSecret;

    @Value("${keycloak.client.username}")
    private String username;

    @Value("${keycloak.client.password}")
    private String password;


    @Value("${keycloak.resource-server.jwt.issuer-uri}")
    private String issuerUri;


}
