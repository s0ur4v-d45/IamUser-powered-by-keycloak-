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
    public ResponseEntity<List<RoleDTO>> findAllRealmRoles(@PathVariable String realm){
        return ResponseEntity.ok(roleService.findAllRealmRoles(realm));
    }

    @GetMapping("/client/{realm}")
    public ResponseEntity<List<RoleDTO>> findAllClientRoles(@PathVariable String realm){
        return ResponseEntity.ok(roleService.findAllClientRoles(realm));
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTO> findRoleById(@PathVariable String roleId) {
        return ResponseEntity.ok(roleService.findRoleById(roleId));
    }

    @PostMapping("/realm")
    public ResponseEntity<RoleDTO> createRealmRole(@RequestBody RoleDTO roleDTO){
        return ResponseEntity.ok().body(roleService.createRealmRole(roleDTO));
    }

    @PutMapping("/realm/{realmName}/{roleName}")
    public ResponseEntity<RoleDTO> updateRealmRole(@PathVariable String realmName,@PathVariable String roleName,@RequestBody RoleDTO roleDTO){
        roleService.updateRealmRole(realmName,roleName,roleDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/client/{realmName}/{roleName}")
    public ResponseEntity<RoleDTO> createClientRole(@PathVariable String realmName,@PathVariable String roleName){
        roleService.createClientRole(realmName, roleName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/realm/{realmName}/{roleId}")
    public ResponseEntity<Void> deleteRealmRole(@PathVariable String realmName, @PathVariable String roleId){
        roleService.deleteRealmRole(realmName, roleId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/client/{realmName}/{roleName}")
    public ResponseEntity<Void> deleteClientRole(@PathVariable String realmName, @PathVariable String roleName){
        roleService.deleteClientRole(realmName, roleName);
        return ResponseEntity.ok().build();
    }

}
