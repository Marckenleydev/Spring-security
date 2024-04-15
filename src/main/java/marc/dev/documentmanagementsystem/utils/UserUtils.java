package marc.dev.documentmanagementsystem.utils;

import marc.dev.documentmanagementsystem.dto.User;
import marc.dev.documentmanagementsystem.entity.CredentialEntity;
import marc.dev.documentmanagementsystem.entity.RoleEntity;
import marc.dev.documentmanagementsystem.entity.UserEntity;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

import static java.time.LocalDateTime.now;
import static marc.dev.documentmanagementsystem.constant.Constants.NINETY_DAYS;
import static org.apache.logging.log4j.util.Strings.EMPTY;


public class UserUtils {
    public static UserEntity createUserEntity(String firstName,
                                   String lastName,
                                   String email,
                                   RoleEntity role) {

        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .lastLogin(now())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .mfa(false)
                .enabled(false)
                .loginAttempts(0)
                .qrCodeSecret(EMPTY)
                .phone(EMPTY)
                .bio(EMPTY)
                .imageUrl("https://i.pinimg.com/564x/c4/34/d8/c434d8c366517ca20425bdc9ad8a32de.jpg")
                .build();


    }

    public static User fromUserEntity(UserEntity userEntity, RoleEntity roles, CredentialEntity credentialEntity) {
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        user.setLastLogin(userEntity.getLastLogin().toString());
        user.setCredentialNonExpired(isCredentialNonExpired(credentialEntity));
        user.setCreatedAt(userEntity.getCreatedAt().toString());
        user.setUpdatedAt(userEntity.getUpdatedAt().toString());
        user.setRole(roles.getName());
        user.setAuthorities(roles.getAuthorities().getValue());
        return user;
    }

    public static boolean isCredentialNonExpired(CredentialEntity credentialEntity) {
        return credentialEntity.getUpdatedAt().plusDays(NINETY_DAYS).isAfter(now());

    }
}
