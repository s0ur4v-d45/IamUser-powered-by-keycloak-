package com.iam_user_management.keycloak.service;

import com.iam_user_management.keycloak.domain.RealmEntity;
import com.iam_user_management.keycloak.mapper.RealmMapper;
import com.iam_user_management.keycloak.model.RealmDTO;
import com.iam_user_management.keycloak.repo.RealmRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RealmServiceImpl implements RealmService {

    private final Keycloak keycloak;
    private final RealmMapper realmMapper;
    private final RealmRepository realmRepository;
    @Override

    public List<RealmDTO> getAllRealm(final boolean sync) {
        if (sync) {

            List<RealmDTO> keycloakRealm = keycloak.realms().findAll().stream()
                    .map(realmMapper::mapRealmRepresentationToRealmDTO).toList();
            List<RealmDTO> existingRealm = realmRepository.findAll().stream()
                    .map(realmMapper::mapEntityToDTO).toList();
            realmRepository.saveAll(keycloakRealm.stream()
                            .filter(dto -> !existingRealm.contains(dto)).map(realmMapper::mapDTOToEntity).toList());
        }
        return realmRepository.findAll().stream().map(realmMapper::mapEntityToDTO).toList();

    }

    @Override
    public RealmDTO createRealm(final RealmDTO realmDTO) {
        keycloak.realms().create(realmMapper.mapRealmDTOToRealmRepresentation(realmDTO));
        RealmEntity realm = realmMapper.mapDTOToEntity(realmMapper
                .mapRealmRepresentationToRealmDTO(
                        keycloak.realm(realmDTO.getRealm()).toRepresentation()));
        realm = realmRepository.save(realm);
        return realmMapper.mapEntityToDTO(realm);
    }

    @Override
    @Transactional
    public void deleteRealm(final String realmName)  {
        try {
            keycloak.realm(realmName).remove();
            realmRepository.deleteByRealm(realmName);
        } catch (Exception exception) {
            throw new NotFoundException(exception.getMessage());
        }
    }

    @Override
    public RealmDTO getByName(final String realmName) {
        return realmMapper.mapEntityToDTO(realmRepository.findByRealm(realmName).orElseThrow(NotFoundException::new));
    }

    @Override
    public RealmDTO getById(final String id) {
        return realmMapper.mapEntityToDTO(realmRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public RealmDTO updateRealm(final String realmName ,final RealmDTO realmDTO) {
        keycloak.realm(realmName).update(realmMapper.mapRealmDTOToRealmRepresentation(realmDTO));
        RealmEntity entity = realmRepository.findByRealm(realmName).orElseThrow(NotFoundException::new);
        entity.setRealm( !realmDTO.getRealm().isEmpty() ? realmDTO.getRealm() : entity.getRealm());
        entity.setDisplayName(realmDTO.getDisplayName() != null ? realmDTO.getDisplayName() : entity.getDisplayName());
        entity = realmRepository.save(entity);
        return realmMapper.mapEntityToDTO(entity);
    }
}
