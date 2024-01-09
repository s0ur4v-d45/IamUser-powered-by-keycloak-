package com.iam_user_management.keycloak.controller;

import com.iam_user_management.keycloak.model.ClientDTO;
import com.iam_user_management.keycloak.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> create(@RequestBody final ClientDTO clientDTO) {
        return ResponseEntity.ok().body(clientService.createClient(clientDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable final String id) {
        return ResponseEntity.ok().body(clientService.findById(id));
    }

    @DeleteMapping("/{id}/{realm}")
    public ResponseEntity<Void> deleteClient(@PathVariable final String id, @PathVariable final String realm) {
        clientService.deleteClient(id,realm);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClient(@RequestParam final Boolean sync) {
        return ResponseEntity.ok(clientService.getAllClient(sync));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable final String id , @RequestBody final ClientDTO clientDTO) {
        return ResponseEntity.ok().body(clientService.update(id,clientDTO));
    }
}
