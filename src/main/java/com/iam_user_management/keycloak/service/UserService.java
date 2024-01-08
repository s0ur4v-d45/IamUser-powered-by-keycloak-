package com.iam_user_management.keycloak.service;

import com.iam_user_management.keycloak.model.ResetPasswordDTO;
import com.iam_user_management.keycloak.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findUser(String id);
    UserDTO createUser(UserDTO userDTO);
    void deleteUser(String id,String realm);
    List<UserDTO> findUserByEmail(String email);
    List<UserDTO> findAllUsers(boolean sync);
    void saveCredentials(ResetPasswordDTO resetPasswordDTO);
    void emailVerification(String myRealm, String userId);

}
