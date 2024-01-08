package com.iam_user_management.keycloak.repo;

import com.iam_user_management.keycloak.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,String> {
    List<UserEntity> findByEmail(String email);
}
