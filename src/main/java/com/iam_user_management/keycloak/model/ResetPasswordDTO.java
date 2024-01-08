package com.iam_user_management.keycloak.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {
    private String realm;
    private String userId;
    private Boolean temporary;
    private String type;
    private String value;
}
