package com.iam_user_management.keycloak.service;

import com.iam_user_management.keycloak.model.RealmDTO;

import java.util.List;

public interface RealmService {
    List<RealmDTO>  getAllRealm(boolean sync);
    RealmDTO createRealm(RealmDTO realmDTO);
    void deleteRealm(String realmName);
    RealmDTO getByName(String realmName);
    RealmDTO updateRealm(String realmName ,RealmDTO realmDTO);
    RealmDTO getById(String id);
}
