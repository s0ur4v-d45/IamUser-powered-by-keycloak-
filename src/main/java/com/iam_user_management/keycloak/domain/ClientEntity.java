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
@Getter
@Setter
public class ClientEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;
    private String clientId;
    private String name;
    private String description;

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
