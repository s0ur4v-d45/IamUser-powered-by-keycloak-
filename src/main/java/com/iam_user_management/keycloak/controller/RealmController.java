package com.iam_user_management.keycloak.controller;


import com.iam_user_management.keycloak.model.RealmDTO;
import com.iam_user_management.keycloak.service.RealmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/realm")
@RequiredArgsConstructor
public class RealmController {

    private final RealmService realmService;
    @GetMapping
    public ResponseEntity<List<RealmDTO>> getAllRealms(@RequestParam final Boolean sync) {
        return ResponseEntity.ok().body(realmService.getAllRealm(sync));
    }

    @PostMapping
    public ResponseEntity<RealmDTO> createRealm(@RequestBody final RealmDTO realmDTO) {
        return ResponseEntity.ok().body(realmService.createRealm(realmDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRealm(@RequestParam final String realmName) {
        realmService.deleteRealm(realmName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/name")
    public ResponseEntity<RealmDTO> getRealmByName(@RequestParam final String realmName) {
        return ResponseEntity.ok().body(realmService.getByName(realmName));
    }

    @PutMapping
    public ResponseEntity<RealmDTO> updateRealm(@RequestParam final String realmName,
                                                @RequestBody final RealmDTO realmDTO) {
        return ResponseEntity.ok().body(realmService.updateRealm(realmName ,realmDTO));
    }
    @GetMapping("/{id}")
    public ResponseEntity<RealmDTO> getRealmById(@PathVariable final String id) {
        return ResponseEntity.ok().body(realmService.getById(id));
    }

}

