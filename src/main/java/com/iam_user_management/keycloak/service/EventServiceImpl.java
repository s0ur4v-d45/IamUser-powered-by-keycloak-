package com.iam_user_management.keycloak.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.AdminEventRepresentation;
import org.keycloak.representations.idm.EventRepresentation;
import org.keycloak.representations.idm.RealmEventsConfigRepresentation;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final Keycloak keycloak;

    public List<EventRepresentation> findAllUserEvents(final String realm) {
        RealmResource realmResource = keycloak.realm(realm);
        return realmResource.getEvents();
    }

    @Override
    public List<AdminEventRepresentation> findAllAdminEvents(final String realm) {
        RealmResource realmResource = keycloak.realm(realm);
        return realmResource.getAdminEvents();
    }

    public String manageEventType(final String eventType, final String realm, final boolean enable) {
        RealmResource realmResource = keycloak.realm(realm);
        RealmEventsConfigRepresentation realmEventsConfig = realmResource.getRealmEventsConfig();
        List<String> enabledEventTypes = realmEventsConfig.getEnabledEventTypes();

        if (enable) {
            if (!enabledEventTypes.contains(eventType)) {
                enabledEventTypes.add(eventType);
                realmEventsConfig.setEnabledEventTypes(enabledEventTypes);
                realmResource.updateRealmEventsConfig(realmEventsConfig);
                return eventType + " enabled";
            } else {
                return eventType + " is already enabled";
            }
        } else {
            if (enabledEventTypes != null && enabledEventTypes.contains(eventType)) {
                enabledEventTypes.remove(eventType);
                realmEventsConfig.setEnabledEventTypes(enabledEventTypes);
                realmResource.updateRealmEventsConfig(realmEventsConfig);
                return "Event: " + eventType + " is disabled";
            } else {
                return "Event: " + eventType + " is not enabled or doesn't exist";
            }
        }
    }

    @Override
    public String manageUserEventSettings(final String realmName, final boolean enable) {
        RealmResource realmResource = keycloak.realm(realmName);
        RealmEventsConfigRepresentation realmEventsConfig = realmResource.getRealmEventsConfig();

        if (realmEventsConfig.isEventsEnabled() == enable) {
            return "User event settings are already " + (enable ? "enabled" : "disabled");
        }

        realmEventsConfig.setEventsEnabled(enable);
        realmResource.updateRealmEventsConfig(realmEventsConfig);
        return "User event settings " + (enable ? "enabled" : "disabled");
    }

    @Override
    public String adjustUserEventExpiration(final String realm,final Long expiration) {
        RealmResource realmResource = keycloak.realm(realm);
        RealmEventsConfigRepresentation realmEventsConfig = realmResource.getRealmEventsConfig();

        if (Boolean.FALSE.equals(realmEventsConfig.isEventsEnabled())) {
            return "User event settings are disabled, turn it on first";
        }
        realmEventsConfig.setEventsExpiration(expiration);
        realmResource.updateRealmEventsConfig(realmEventsConfig);
        return "User event expiration adjusted";
    }

    @Override
    public String manageAdminEventSettings(final String realmName, final boolean enable) {
        RealmResource realmResource = keycloak.realm(realmName);
        RealmEventsConfigRepresentation realmEventsConfig = realmResource.getRealmEventsConfig();

        if (realmEventsConfig.isAdminEventsEnabled() == enable) {
            return "Admin event settings are already " + (enable ? "enabled" : "disabled");
        }

        realmEventsConfig.setAdminEventsEnabled(enable);
        realmResource.updateRealmEventsConfig(realmEventsConfig);
        return "Admin event settings " + (enable ? "enabled" : "disabled");
    }

    @Override
    public String manageAdminEventDetailedSettings(final String realmName, final boolean enable) {
        RealmResource realmResource = keycloak.realm(realmName);
        RealmEventsConfigRepresentation realmEventsConfig = realmResource.getRealmEventsConfig();

        if (Boolean.FALSE.equals(realmEventsConfig.isAdminEventsEnabled())) {
            return "Admin event settings are disabled, turn it on first";
        } else if (realmEventsConfig.isAdminEventsDetailsEnabled() == enable) {
            return "Admin detailed event settings are already " + (enable ? "enabled" : "disabled");
        }
        realmEventsConfig.setAdminEventsDetailsEnabled(enable);
        realmResource.updateRealmEventsConfig(realmEventsConfig);
        return "Admin event detailed settings " + (enable ? "enabled" : "disabled");
    }

    @Override
    public List<String> fetchEventListeners(final String realmName) {
        RealmResource realmResource = keycloak.realm(realmName);
        RealmEventsConfigRepresentation realmEventsConfig = realmResource.getRealmEventsConfig();
        return realmEventsConfig.getEventsListeners();
    }

}
