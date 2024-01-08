package com.iam_user_management.keycloak.model;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;


@Getter
@Setter
public class RoleDTO {
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String realmName;
    private String description;
    private Boolean clientRole;
    private String containerId;
    private Map<String, List<String>> attributes;
}

