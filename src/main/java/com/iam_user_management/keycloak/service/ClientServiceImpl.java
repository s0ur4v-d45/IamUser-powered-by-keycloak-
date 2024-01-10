package com.iam_user_management.keycloak.service;

import com.iam_user_management.keycloak.domain.ClientEntity;
import com.iam_user_management.keycloak.mapper.ClientMapper;
import com.iam_user_management.keycloak.model.ClientDTO;
import com.iam_user_management.keycloak.model.RealmDTO;
import com.iam_user_management.keycloak.repo.ClientRepository;
import com.iam_user_management.keycloak.repo.RealmRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final Keycloak keycloak;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final RealmRepository realmRepository;
    private final RealmService realmService;

    @Override
    public ClientDTO findById(final String id) {
        return clientMapper.mapEntityToDTO(clientRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public ClientDTO createClient(final ClientDTO clientDTO) {
        keycloak.realm(clientDTO.getRealm())
                .clients()
                        .create(clientMapper.mapClientDtoToClientRepresentation(clientDTO));
        ClientDTO createdClient = clientMapper.mapClientRepresentationToClientDto(keycloak.realm(clientDTO.getRealm())
                .clients().findByClientId(clientDTO.getClientId()).get(0));
        createdClient.setRealm(clientDTO.getRealm());
        ClientEntity client = clientMapper.mapDTOToEntity(createdClient, new ClientEntity(), realmRepository);
        client = clientRepository.save(client);
        return clientMapper.mapEntityToDTO(client);
    }

    @Override
    public void deleteClient(final String id, final String myRealm) {
        try {
            keycloak.realm(myRealm).clients().get(id).remove();
            clientRepository.deleteById(id);
        } catch (Exception exception) {
            throw new NotFoundException(exception.getMessage());
        }
    }

    @Override
    public List<ClientDTO> getAllClient(final boolean sync) {
        if (sync) {
            List<RealmDTO> realms = realmService.getAllRealm(sync);
            realms.forEach(realmDTO -> {

                List<ClientDTO> serverData = keycloak.realm(realmDTO.getRealm()).clients().findAll()
                        .stream()
                        .map(clientMapper::mapClientRepresentationToClientDto)
                        .toList();
                serverData
                        .forEach(userDTO -> userDTO.setRealm(realmDTO.getRealm()));
                List<ClientDTO> dbData = clientRepository.findAll().stream().map(clientMapper::mapEntityToDTO).toList();

                serverData.stream()
                        .filter(data -> !dbData.contains(data))
                        .forEach(data -> clientRepository.save(clientMapper.mapDTOToEntity(data,
                                new ClientEntity(), realmRepository)));

            });
        }
        return clientRepository.findAll().stream()
                .map(clientMapper::mapEntityToDTO).toList();


    }

    @Override
    public ClientDTO  update(final String id ,final ClientDTO clientDTO) {
        keycloak.realm(clientRepository.findById(id).orElseThrow(NotFoundException::new).getRealm().getRealm())
                .clients().get(id).update(clientMapper.mapClientDtoToClientRepresentation(clientDTO));
        ClientEntity client = clientRepository.findById(id).orElseThrow(NotFoundException::new);
        client.setClientId(clientDTO.getClientId()  != null ? clientDTO.getClientId() : client.getClientId());
        client.setName(clientDTO.getName()  != null ? clientDTO.getName() : client.getName() );
        client.setDescription(clientDTO.getDescription()  != null
                ? clientDTO.getDescription()
                : client.getDescription());
        client = clientRepository.save(client);
        return clientMapper.mapEntityToDTO(client);
    }


}
