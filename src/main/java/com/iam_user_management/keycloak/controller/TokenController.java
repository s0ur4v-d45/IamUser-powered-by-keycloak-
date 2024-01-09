package com.iam_user_management.keycloak.controller;

import com.iam_user_management.keycloak.model.TokenDTO;
import com.iam_user_management.keycloak.service.AccessTokenService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/keycloak/token")
@RequiredArgsConstructor
public class TokenController {

    private final AccessTokenService accessTokenService;
    @PostMapping
    public ResponseEntity<AccessTokenResponse> generateToken(@RequestBody final TokenDTO tokenDTO) {
        return ResponseEntity.ok(accessTokenService.generateToken(tokenDTO));
    }
}
