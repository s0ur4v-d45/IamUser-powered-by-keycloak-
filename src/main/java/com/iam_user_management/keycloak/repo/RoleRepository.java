package com.iam_user_management.keycloak.repo;

import com.iam_user_management.keycloak.domain.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    List<RoleEntity> findAllByClientRoleFalseAndRealmRealm(String realm);
    Optional<RoleEntity> findByNameAndRealmRealm(String roleName ,String realm);
    Optional<RoleEntity> findByNameAndRealmRealmAndClientRoleTrue(String roleNmae ,String realm);
    Optional<RoleEntity> findByNameAndRealmRealmAndClientRoleFalse(String roleNmae ,String realm);
    void deleteByName(String roleName);
    List<RoleEntity> findAllByClientRoleTrueAndRealmRealm(String realm);

}

