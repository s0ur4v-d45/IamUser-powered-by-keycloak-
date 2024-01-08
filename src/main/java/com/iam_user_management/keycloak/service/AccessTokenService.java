package com.iam_user_management.keycloak.service;

import com.iam_user_management.keycloak.model.TokenDTO;
import org.keycloak.representations.AccessTokenResponse;

public interface AccessTokenService {
    AccessTokenResponse generateToken(TokenDTO tokenDTO);
}
