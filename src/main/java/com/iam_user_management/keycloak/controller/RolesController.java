package com.iam_user_management.keycloak.controller;

import com.iam_user_management.keycloak.model.RoleDTO;
import com.iam_user_management.keycloak.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keycloak/roles")
@RequiredArgsConstructor
public class RolesController {

    private final RoleService roleService;
    @GetMapping("/realm/{realm}")
    public ResponseEntity<List<RoleDTO>> findAllRealmRoles(@PathVariable final String realm) {
        return ResponseEntity.ok(roleService.findAllRealmRoles(realm));
    }

    @GetMapping("/client/{realm}")
    public ResponseEntity<List<RoleDTO>> findAllClientRoles(@PathVariable final String realm) {
        return ResponseEntity.ok(roleService.findAllClientRoles(realm));
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTO> findRoleById(@PathVariable final String roleId) {
        return ResponseEntity.ok(roleService.findRoleById(roleId));
    }

    @PostMapping("/realm")
    public ResponseEntity<RoleDTO> createRealmRole(@RequestBody final RoleDTO roleDTO) {
        return ResponseEntity.ok().body(roleService.createRealmRole(roleDTO));
    }

    @PutMapping("/realm/{realmName}/{roleName}")
    public ResponseEntity<RoleDTO> updateRealmRole(@PathVariable final String realmName,
                                                   @PathVariable final String roleName,
                                                   @RequestBody final RoleDTO roleDTO) {
        roleService.updateRealmRole(realmName,roleName,roleDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/client/{realmName}/{roleName}")
    public ResponseEntity<RoleDTO> createClientRole(@PathVariable final String realmName,
                                                    @PathVariable final String roleName) {
        roleService.createClientRole(realmName, roleName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/realm/{realmName}/{roleId}")
    public ResponseEntity<Void> deleteRealmRole(@PathVariable final String realmName,
                                                @PathVariable final String roleId) {
        roleService.deleteRealmRole(realmName, roleId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/client/{realmName}/{roleName}")
    public ResponseEntity<Void> deleteClientRole(@PathVariable final String realmName,
                                                 @PathVariable final String roleName) {
        roleService.deleteClientRole(realmName, roleName);
        return ResponseEntity.ok().build();
    }

}
