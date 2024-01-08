package com.iam_user_management.keycloak.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Entity
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public class RoleEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;
    @Column(nullable = false)
    private String name;
    private String description;
    private Boolean clientRole;
    private String containerId;
    @ManyToOne
    @JoinColumn(name = "realm_id", nullable = false)
    private RealmEntity realm;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastUpdated;
}
