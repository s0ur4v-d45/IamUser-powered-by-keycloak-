package com.iam_user_management.keycloak.service;


import com.iam_user_management.keycloak.domain.UserEntity;
import com.iam_user_management.keycloak.mapper.UserMapper;
import com.iam_user_management.keycloak.model.RealmDTO;
import com.iam_user_management.keycloak.model.ResetPasswordDTO;
import com.iam_user_management.keycloak.model.UserDTO;
import com.iam_user_management.keycloak.repo.RealmRepository;
import com.iam_user_management.keycloak.repo.UserRepository;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final Keycloak keycloakClient;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RealmRepository realmRepository;
    private final RealmService realmService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO findUser(final String id) {
        return userMapper.mapEntityToDTO(userRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public UserDTO createUser(final UserDTO userDTO) {
        keycloakClient.realm(userDTO.getRealm())
                .users().create(userMapper.mapUserDtoToUserRepresentation(userDTO));
        UserDTO createdUser = userMapper.mapUserRepresentationToUserDto(keycloakClient.realm(userDTO.getRealm()).users()
                .search(userDTO.getUsername()).get(0));
        createdUser.setRealm(userDTO.getRealm());
        UserEntity user = userMapper.mapDTOToEntity(createdUser, new UserEntity(),realmRepository);
        user = userRepository.save(user);
        return userMapper.mapEntityToDTO(user);
    }

    @Override
    public void deleteUser(final String id, final String myRealm) {
        try {
            keycloakClient.realm(myRealm).users().delete(id);
            userRepository.deleteById(id);
        } catch (Exception exception) {
            throw new NotFoundException(exception.getMessage());
        }

    }

    @Override
    public List<UserDTO> findUserByEmail(final String email) {
        return userRepository.findByEmail(email).stream().map(userMapper::mapEntityToDTO).toList();
    }
    @Override
    public List<UserDTO> findAllUsers(final boolean sync) {
        if (sync) {
            List<RealmDTO> realms = realmService.getAllRealm(sync);
            realms.forEach(realmDTO -> {

                List<UserDTO> serverData = keycloakClient.realm(realmDTO.getRealm()).users()
                        .list()
                        .stream()
                        .map(userMapper::mapUserRepresentationToUserDto)
                        .toList();
                serverData
                        .forEach(userDTO -> userDTO.setRealm(realmDTO.getRealm()));
                List<UserDTO> dbData = userRepository.findAll().stream().map(userMapper::mapEntityToDTO).toList();

                serverData.stream()
                        .filter(data -> !dbData.contains(data))
                        .forEach(data -> userRepository.save(userMapper
                                .mapDTOToEntity(data,new UserEntity(),realmRepository)));

            });
        }
        return  userRepository.findAll().stream()
                .map(userMapper::mapEntityToDTO).toList();
    }

    public void saveCredentials(final ResetPasswordDTO resetPasswordDTO) {
        Optional<UserEntity> savedUserOptional = userRepository.findById(resetPasswordDTO.getUserId());

        if (savedUserOptional.isPresent()) {
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(resetPasswordDTO.getValue());
            credential.setTemporary(resetPasswordDTO.getTemporary());

            keycloakClient.realm(resetPasswordDTO.getRealm())
                    .users()
                    .get(resetPasswordDTO.getUserId())
                    .resetPassword(credential);


            UserEntity existingUser = savedUserOptional.get();
            existingUser.setPasswordHash(passwordEncoder.encode(resetPasswordDTO.getValue()));
            userRepository.save(existingUser);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void emailVerification(final String myRealm, final String userId) {
        Optional<UserEntity> savedUserOptional = userRepository.findById(userId);

        if (savedUserOptional.isPresent()) {
            keycloakClient.realm(myRealm).users()
                    .get(userId)
                    .sendVerifyEmail();
        } else {
            throw new NotFoundException();
        }
    }
}
