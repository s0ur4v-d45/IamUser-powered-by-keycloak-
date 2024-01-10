package com.iam_user_management.keycloak.service;

import com.iam_user_management.keycloak.config.KeycloakProperties;
import com.iam_user_management.keycloak.mapper.RoleMapper;
import com.iam_user_management.keycloak.model.RoleDTO;
import com.iam_user_management.keycloak.repo.RoleRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final Keycloak keycloakClient;
    private final RoleMapper roleMapper;
    private final KeycloakProperties keycloakProperties;
    private final RoleRepository roleRepository;

    @Override
    public List<RoleDTO> findAllRealmRoles(final String realm) {
        return roleRepository.findAllByClientRoleFalseAndRealmRealm(realm).stream().map(roleMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public RoleDTO findRoleById(final String roleId) {
        return roleMapper.mapEntityToDto(roleRepository.findById(roleId).orElseThrow(NotFoundException::new));
    }

    @Override
    public RoleDTO createRealmRole(final RoleDTO roleDTO) {
        keycloakClient.realm(roleDTO.getRealmName()).roles().create(roleMapper.mapRoleDtoToRoleRepresentation(roleDTO));
        RoleDTO createdRealmRole = roleMapper.mapRoleRepresentationToRoleDto(
                keycloakClient.realm(roleDTO.getRealmName()).roles().get(roleDTO.getRealmName()).toRepresentation());
        roleRepository.save(roleMapper.mapDtoToEntity(createdRealmRole));
        return createdRealmRole;
    }

    @Override
    public void updateRealmRole(final String realmName,final  String roleName,final RoleDTO updatedRole) {
        RoleDTO existingRole = roleMapper.mapEntityToDto(
                roleRepository.findByNameAndRealmRealmAndClientRoleFalse(roleName,realmName)
                        .orElseThrow(NotFoundException::new));
        existingRole.setDescription(updatedRole.getDescription() != null
                 ? updatedRole.getDescription()
                 : existingRole.getDescription());
        existingRole.setAttributes(updatedRole.getAttributes() != null
                ? updatedRole.getAttributes()
                : existingRole.getAttributes());
        roleRepository.save(roleMapper.mapDtoToEntity(existingRole));
        keycloakClient.realm(realmName).roles().get(roleName)
                .update(roleMapper.mapRoleDtoToRoleRepresentation(existingRole));
    }

    @Override
    public void deleteRealmRole(final String realmName, final String roleName) {
        roleRepository.findByNameAndRealmRealm(roleName , realmName).orElseThrow(NotFoundException::new);
        keycloakClient.realm(realmName).roles().get(roleName).remove();
        roleRepository.deleteByName(roleName);
    }

    @Override
    public RoleDTO createClientRole(final String realmName,final String roleName) {

        RealmResource realmResource = keycloakClient.realm(realmName);
        ClientResource clientResource = realmResource.clients().get(keycloakProperties.getContainerId());

        RolesResource rolesResource = clientResource.roles();

        List<RoleRepresentation> roleList = rolesResource.list();
        if (roleList == null || roleList.isEmpty()) {
            RoleRepresentation roloRep = new RoleRepresentation(roleName, roleName, true);
            rolesResource.create(roloRep);
        } else {
            boolean exists = false;
            for (RoleRepresentation r : roleList) {
                if (r.getName().equals(roleName)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                RoleRepresentation newRole = new RoleRepresentation(roleName, roleName, true);
                rolesResource.create(newRole);
            }
        }
        RoleDTO createdRole = roleMapper.mapRoleRepresentationToRoleDto(keycloakClient.realm(realmName)
                .clients().get(keycloakProperties.getContainerId())
                .roles()
                .get(roleName).toRepresentation());
        roleRepository.save(roleMapper.mapDtoToEntity(createdRole));
        return createdRole;
    }

    @Override
    public List<RoleDTO> findAllClientRoles(final String realm) {
        return roleRepository.findAllByClientRoleTrueAndRealmRealm(realm)
                .stream()
                .map(roleMapper::mapEntityToDto).toList();
    }

    @Override
    public void deleteClientRole(final String realmName, final String roleName) {
        roleRepository.findByNameAndRealmRealmAndClientRoleTrue(roleName , realmName)
                .orElseThrow(NotFoundException::new);
        keycloakClient.realm(realmName).clients().get(keycloakProperties.getContainerId())
                .roles().deleteRole(roleName);
        roleRepository.deleteByName(roleName);
    }
}