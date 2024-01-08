package com.iam_user_management.keycloak.mapper;

import com.iam_user_management.keycloak.domain.UserEntity;
import com.iam_user_management.keycloak.model.UserDTO;
import com.iam_user_management.keycloak.repo.RealmRepository;
import jakarta.ws.rs.NotFoundException;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "realm" , ignore=true)
    UserEntity mapDTOToEntity(UserDTO userDTO,@MappingTarget UserEntity userEntity, @Context RealmRepository realmRepository);
    @AfterMapping
    default  void  afterMapDTOToEntity(UserDTO userDTO , @MappingTarget UserEntity userEntity, @Context RealmRepository realmRepository){
        userEntity.setRealm(realmRepository.findByRealm(userDTO.getRealm()).orElseThrow(NotFoundException::new));
    }
    @Mapping(target = "realm" , ignore=true)
    UserDTO mapEntityToDTO(UserEntity userEntity);
    @AfterMapping
    default  void  afterMapEntityToDTO(UserEntity userEntity ,@MappingTarget UserDTO userDTO){
        userDTO.setRealm(userEntity.getRealm().getRealm());
    }

    UserRepresentation mapUserDtoToUserRepresentation(UserDTO userDTO);
    UserDTO mapUserRepresentationToUserDto(UserRepresentation userRepresentation);

}
