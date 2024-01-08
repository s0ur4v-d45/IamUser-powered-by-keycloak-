package com.iam_user_management.keycloak.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealmDTO {
    private String id;
    @NotNull
    private String realm;
    private String displayName;
    
}
