package com.iam_user_management.keycloak.controller;


import com.iam_user_management.keycloak.service.EventService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.AdminEventRepresentation;
import org.keycloak.representations.idm.EventRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keycloak/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/user")
    public ResponseEntity<List<EventRepresentation>> findAllUserEvents(@RequestParam final String realmName) {
        return ResponseEntity.ok(eventService.findAllUserEvents(realmName));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<AdminEventRepresentation>> findAllAdminEvents(@RequestParam final String realmName) {
        return ResponseEntity.ok(eventService.findAllAdminEvents(realmName));
    }

    @PostMapping("/{eventType}")
    public ResponseEntity<String> manageEventType(@PathVariable final String eventType,
                                                  @RequestParam final String realmName,
                                                  @RequestParam final boolean enable) {
        return ResponseEntity.ok().body(eventService.manageEventType(eventType, realmName, enable));
    }

    @PostMapping("/user-event-expiration/{realm}")
    public ResponseEntity<String> adjustUserEventExpiration(
            @PathVariable final String realm,
            @RequestParam final Long expiration) {
        return new ResponseEntity<>(eventService.adjustUserEventExpiration(realm, expiration),
                HttpStatus.OK);
    }

    @PostMapping("/user-event-settings/{realm}")
    public ResponseEntity<String> manageUserEventSettings(
            @PathVariable final String realm,
            @RequestParam final boolean enable) {
        return new ResponseEntity<>( eventService.manageUserEventSettings(realm, enable),
                HttpStatus.OK);
    }

    @PostMapping("/admin-event-settings/{realm}")
    public ResponseEntity<String> manageAdminEventSettings(
            @PathVariable final String realm,
            @RequestParam final boolean enable) {
        return new ResponseEntity<>(eventService.manageAdminEventSettings(realm, enable),
                HttpStatus.OK);
    }

    @PostMapping("/admin-event-detailed-settings/{realm}")
    public ResponseEntity<String> manageAdminEventDetailedSettings(
            @PathVariable final String realm,
            @RequestParam final boolean enable) {
        return new ResponseEntity<>(eventService.manageAdminEventDetailedSettings(realm, enable),
                HttpStatus.OK);
    }

    @GetMapping("/event-listeners/{realm}")
    public ResponseEntity<List<String>> fetchEventListeners(
            @PathVariable final String realm) {
        List<String> eventListeners = eventService.fetchEventListeners(realm);
        return new ResponseEntity<>(eventListeners, HttpStatus.OK);
    }
}
