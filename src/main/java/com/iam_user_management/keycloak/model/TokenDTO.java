package com.iam_user_management.keycloak.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {
    private String username;
    private String password;
    private String realmName;
}
