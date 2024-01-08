package com.iam_user_management.keycloak.repo;

import com.iam_user_management.keycloak.domain.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity,String> {
}
