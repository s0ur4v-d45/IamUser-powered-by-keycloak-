package com.iam_user_management.keycloak.controller;

import com.iam_user_management.keycloak.model.ResetPasswordDTO;
import com.iam_user_management.keycloak.model.UserDTO;
import com.iam_user_management.keycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keycloak/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUser(@PathVariable final String id) {
        return ResponseEntity.ok(userService.findUser(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody final UserDTO userDTO) {
        return ResponseEntity.ok().body(userService.createUser(userDTO));
    }

    @DeleteMapping("/{id}/{realm}")
    public ResponseEntity<Void> deleteUser(@PathVariable final String id, @PathVariable final String realm) {
        userService.deleteUser(id,realm);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/email")
    public ResponseEntity<List<UserDTO>> findByEmail(@RequestParam final String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));

    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> syncAllUser(@RequestParam final Boolean sync) {
        return ResponseEntity.ok(userService.findAllUsers(sync));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody final ResetPasswordDTO resetPasswordDTO) {
        userService.saveCredentials(resetPasswordDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestParam final String realm, @RequestParam final String userId) {
        userService.emailVerification(realm,userId);
        return ResponseEntity.ok().build();
    }


}
