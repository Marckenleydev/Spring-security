package marc.dev.documentmanagementsystem.service;

import marc.dev.documentmanagementsystem.dto.User;
import marc.dev.documentmanagementsystem.entity.CredentialEntity;
import marc.dev.documentmanagementsystem.entity.RoleEntity;
import marc.dev.documentmanagementsystem.entity.UserEntity;
import marc.dev.documentmanagementsystem.enumaration.LoginType;


public interface UserService {
    void createUser(String firstName, String lastName, String email, String password);
    RoleEntity getRoleName(String name );

    User getUserByUserId(String userId);
    User getUserByEmail(String email);
    CredentialEntity getUserCredentialById(Long userId);


    CredentialEntity getUserCredentialByUserId(Long userId);

    void verifyUserAccount(String token);
    void updateLoginAttempt(String email, LoginType loginType);
}
