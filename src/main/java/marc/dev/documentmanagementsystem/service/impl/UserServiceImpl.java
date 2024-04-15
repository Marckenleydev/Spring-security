package marc.dev.documentmanagementsystem.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marc.dev.documentmanagementsystem.cache.CacheStore;
import marc.dev.documentmanagementsystem.domain.RequestContext;
import marc.dev.documentmanagementsystem.dto.User;
import marc.dev.documentmanagementsystem.entity.ConfirmationEntity;
import marc.dev.documentmanagementsystem.entity.CredentialEntity;
import marc.dev.documentmanagementsystem.entity.RoleEntity;
import marc.dev.documentmanagementsystem.entity.UserEntity;
import marc.dev.documentmanagementsystem.enumaration.Authority;
import marc.dev.documentmanagementsystem.enumaration.LoginType;
import marc.dev.documentmanagementsystem.enumaration.converter.EventType;
import marc.dev.documentmanagementsystem.event.UserEvent;
import marc.dev.documentmanagementsystem.exception.ApiException;
import marc.dev.documentmanagementsystem.repository.ConfirmationRepository;
import marc.dev.documentmanagementsystem.repository.CredentialRepository;
import marc.dev.documentmanagementsystem.repository.RoleRepository;
import marc.dev.documentmanagementsystem.repository.UserRepository;
import marc.dev.documentmanagementsystem.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static marc.dev.documentmanagementsystem.utils.UserUtils.createUserEntity;
import static marc.dev.documentmanagementsystem.utils.UserUtils.fromUserEntity;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConfirmationRepository confirmationRepository;
    private final CredentialRepository credentialRepository;
    private final ApplicationEventPublisher publisher;

    private final CacheStore<String, Integer> userCache;


    @Override
    public void createUser(String firstName, String lastName, String email, String password) {

        var userEntity = userRepository.save(createNewUser(firstName, lastName, email));
        var credentialEntity = new CredentialEntity(userEntity,password);
        credentialRepository.save(credentialEntity);
        var confirmation = new ConfirmationEntity(userEntity);
        confirmationRepository.save(confirmation);
        publisher.publishEvent(new UserEvent(userEntity, EventType.REGISTRATION, Map.of("key", confirmation.getToken())));
        
    }

    @Override
    public RoleEntity getRoleName(String name) {
        var role = roleRepository.findByNameIgnoreCase(name);
        return role.orElseThrow(()-> new ApiException("Role not found"));
    }

    @Override
    public User getUserByUserId(String userId) {
        var userEntity = userRepository.findUserByUserId(userId).orElseThrow(()-> new ApiException("User not Found"));
        return fromUserEntity(userEntity, userEntity.getRoles(), getUserCredentialById(userEntity.getId()));
    }

    @Override
    public User getUserByEmail(String email) {

        UserEntity userEntity = getUserEntityByEmail(email);
        return fromUserEntity(userEntity, userEntity.getRoles(), getUserCredentialById(userEntity.getId()));
    }



    @Override
    public CredentialEntity getUserCredentialById(Long userId) {
        var credential = credentialRepository.getCredentialByUserEntityId(userId);
        return credential.orElseThrow(()-> new ApiException("Unable to find credential"));
    }



    @Override
    public void verifyUserAccount(String token) {
       var confirmationEntity = getUserConfirmation(token);
       UserEntity userEntity = getUserEntityByEmail(confirmationEntity.getUserEntity().getEmail());
       userEntity.setEnabled(true);
       userRepository.save(userEntity);
       confirmationRepository.delete(confirmationEntity);

    }

    @Override
    public void updateLoginAttempt(String email, LoginType loginType) {
        var userEntity = getUserEntityByEmail(email);
        RequestContext.setUserId(userEntity.getId());

        switch (loginType){
            case LOGIN_ATTEMPT -> {
                if(userCache.get(userEntity.getEmail()) == null){
                    userEntity.setLoginAttempts(0);
                    userEntity.setAccountNonLocked(true);
                }

                userEntity.setLoginAttempts(userEntity.getLoginAttempts() + 1);
                userCache.put(userEntity.getEmail(), userEntity.getLoginAttempts());
                if(userCache.get(userEntity.getEmail()) > 5){
                    userEntity.setAccountNonLocked(false);
                }
            }
            case LOGIN_SUCCESS -> {
                userEntity.setAccountNonLocked(true);
                userEntity.setLoginAttempts(0);
                userEntity.setLastLogin(LocalDateTime.now());
                userCache.evict( userEntity.getEmail());
            }
        }
        userRepository.save(userEntity);

    }

    private UserEntity getUserEntityByEmail(String email) {
        var userByEmail = userRepository.findByEmailIgnoreCase(email);
        return userByEmail.orElseThrow(()-> new ApiException(" User Not Found"));
    }

    private ConfirmationEntity getUserConfirmation(String token) {
        return confirmationRepository.findByToken(token).orElseThrow(()-> new ApiException("Confirmation not found"));

    }

    private UserEntity createNewUser(String firstName, String lastName, String email) {
        var role = getRoleName(Authority.USER.name());
        return createUserEntity(firstName, lastName, email, role);
    }


}
