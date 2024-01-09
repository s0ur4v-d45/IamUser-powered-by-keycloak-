package com.iam_user_management.keycloak.mapper;

import com.iam_user_management.keycloak.domain.RoleEntity;
import com.iam_user_management.keycloak.model.RoleDTO;
import org.keycloak.representations.idm.RoleRepresentation;
import org.mapstruct.Mapper;


@Mapper(
        componentModel = "spring"
    )
public interface RoleMapper {
    RoleDTO mapRoleRepresentationToRoleDto(RoleRepresentation roleRepresentation);
    RoleRepresentation mapRoleDtoToRoleRepresentation(RoleDTO roleDTO);
    RoleDTO mapEntityToDto(RoleEntity roleEntity);
    RoleEntity mapDtoToEntity(RoleDTO roleDTO);

}