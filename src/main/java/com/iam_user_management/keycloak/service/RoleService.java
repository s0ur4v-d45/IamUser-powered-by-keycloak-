package com.iam_user_management.keycloak.service;

import com.iam_user_management.keycloak.model.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> findAllRealmRoles(String realm);

    RoleDTO findRoleById(String roleId);

    RoleDTO createRealmRole(RoleDTO roleDTO);

    void deleteRealmRole(String realmName, String roleName);

    void updateRealmRole(String realmName, String roleName, RoleDTO updatedRole);

    RoleDTO createClientRole(String realmName, String roleName);

    List<RoleDTO> findAllClientRoles(String realm);

    void deleteClientRole(String realmName, String roleName);

}
