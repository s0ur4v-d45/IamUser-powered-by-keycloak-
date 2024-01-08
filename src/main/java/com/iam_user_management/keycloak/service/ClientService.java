package com.iam_user_management.keycloak.service;

import com.iam_user_management.keycloak.model.ClientDTO;

import java.util.List;

public interface ClientService {
    ClientDTO findById(String id);
    ClientDTO createClient(ClientDTO clientDTO);
    void deleteClient(String id,String realm);
    List<ClientDTO> getAllClient(boolean sync);
    ClientDTO update(String id ,ClientDTO clientDTO);
}
