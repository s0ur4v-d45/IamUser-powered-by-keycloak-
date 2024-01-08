package com.iam_user_management.keycloak.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class RealmEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;
    @Column(nullable = false)
    private String realm;
    private String displayName;
    @OneToMany(mappedBy = "realm",fetch = FetchType.LAZY)
    private List<UserEntity> users;
    @OneToMany(mappedBy = "realm",fetch = FetchType.LAZY)
    private List<RoleEntity> roles;
    @OneToMany(mappedBy = "realm",fetch = FetchType.LAZY)
    private List<ClientEntity> clients;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastUpdated;
}
