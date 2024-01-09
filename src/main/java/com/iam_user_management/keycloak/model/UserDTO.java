package com.iam_user_management.keycloak.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private String id;
    private String email;
    private String realm;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    @NotNull
    private String username;

}
