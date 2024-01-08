package com.iam_user_management.keycloak.mapper;

import com.iam_user_management.keycloak.domain.ClientEntity;
import com.iam_user_management.keycloak.model.ClientDTO;
import com.iam_user_management.keycloak.repo.RealmRepository;
import jakarta.ws.rs.NotFoundException;
import org.keycloak.representations.idm.ClientRepresentation;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {
    @Mapping(target = "realm" , ignore=true)
    ClientEntity mapDTOToEntity(ClientDTO clientDTO, @MappingTarget ClientEntity clientEntity, @Context RealmRepository realmRepository);
    @AfterMapping
    default  void  afterMapDTOToEntity(ClientDTO clientDTO, @MappingTarget ClientEntity clientEntity, @Context RealmRepository realmRepository){
        clientEntity.setRealm(realmRepository.findByRealm(clientDTO.getRealm()).orElseThrow(NotFoundException::new));
    }
    @Mapping(target = "realm" , ignore=true)
    ClientDTO mapEntityToDTO(ClientEntity clientEntity);
    @AfterMapping
    default  void  afterMapEntityToDTO(ClientEntity clientEntity ,@MappingTarget ClientDTO clientDTO){
        clientDTO.setRealm(clientEntity.getRealm().getRealm());
    }

    ClientRepresentation mapClientDtoToClientRepresentation(ClientDTO clientDTO);
    ClientDTO mapClientRepresentationToClientDto(ClientRepresentation clientRepresentation);

}
