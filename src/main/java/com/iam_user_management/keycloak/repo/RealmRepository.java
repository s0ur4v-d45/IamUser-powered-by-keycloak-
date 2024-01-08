package com.iam_user_management.keycloak.repo;

import com.iam_user_management.keycloak.domain.RealmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RealmRepository extends JpaRepository<RealmEntity, String> {
    void deleteByRealm(String name);
    Optional<RealmEntity> findByRealm(String name);

}
