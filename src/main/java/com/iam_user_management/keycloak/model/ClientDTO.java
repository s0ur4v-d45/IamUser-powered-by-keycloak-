package com.iam_user_management.keycloak.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {

    private String id;
    @NotNull
    private String clientId;
    private String name;
    private String description;
    private String realm;
}
