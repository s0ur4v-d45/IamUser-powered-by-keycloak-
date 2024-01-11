package com.iam_user_management.keycloak.service;

import org.keycloak.representations.idm.AdminEventRepresentation;
import org.keycloak.representations.idm.EventRepresentation;

import java.util.List;

public interface EventService {
    List<EventRepresentation> findAllUserEvents(String realm);
    List<AdminEventRepresentation> findAllAdminEvents(String realm);
    String manageEventType( String eventType,  String realm, boolean enable);
    String adjustUserEventExpiration( String realm, Long expiration);
    String manageUserEventSettings(String realmName, boolean enable);
    String manageAdminEventSettings(String realmName, boolean enable);
    String manageAdminEventDetailedSettings(String realmName, boolean enable);
    List<String> fetchEventListeners(String realmName);
}
