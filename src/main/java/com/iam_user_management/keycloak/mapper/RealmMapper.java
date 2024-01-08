package com.iam_user_management.keycloak.mapper;

import com.iam_user_management.keycloak.domain.RealmEntity;
import com.iam_user_management.keycloak.model.RealmDTO;
import org.keycloak.representations.idm.RealmRepresentation;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RealmMapper {

    RealmDTO mapEntityToDTO(RealmEntity realm);
    RealmEntity mapDTOToEntity(RealmDTO realmDTO);
    RealmDTO mapRealmRepresentationToRealmDTO(RealmRepresentation realmRepresentation);

    RealmRepresentation mapRealmDTOToRealmRepresentation(RealmDTO realmDTO);
}