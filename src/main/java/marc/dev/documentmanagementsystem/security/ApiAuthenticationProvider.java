package marc.dev.documentmanagementsystem.security;

import lombok.RequiredArgsConstructor;
import marc.dev.documentmanagementsystem.domain.ApiAuthentication;
import marc.dev.documentmanagementsystem.domain.UserPrincipal;
import marc.dev.documentmanagementsystem.exception.ApiException;
import marc.dev.documentmanagementsystem.service.UserService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Function;

import static marc.dev.documentmanagementsystem.constant.Constants.NINETY_DAYS;

@Component
@RequiredArgsConstructor
public class ApiAuthenticationProvider implements AuthenticationProvider{

        private final UserDetailsService userDetailsService;
        private final UserService userService;
        private final BCryptPasswordEncoder encoder;

        @Override
        public Authentication authenticate(Authentication authentication) {


                var apiAuthentication = authenticationFunction.apply(authentication);
                var user = userService.getUserByEmail(apiAuthentication.getEmail());

                if(user != null){
                        var userCredential = userService.getUserCredentialByUserId(user.getId());
//                        if(userCredential.getUpdatedAt().minusDays(NINETY_DAYS).isAfter(LocalDateTime.now())){
                        if(user.isCredentialNonExpired()){
                                throw new ApiException("Credentials are expired. Please reset your password");
                        }
                        var userPrincipal = new UserPrincipal(user, userCredential);
                        validAccount.accept(userPrincipal);
                        if(encoder.matches(apiAuthentication.getPassword(), userCredential.getPassword())){
                                return ApiAuthentication.authenticated(user, userPrincipal.getAuthorities());
                        }else throw new BadCredentialsException("Email and/or password incorrect. Please try again");

                }else  throw new ApiException("Unable to authenticate");
        };

        private final Function<Authentication, ApiAuthentication> authenticationFunction = authentication->(ApiAuthentication) authentication;

        @Override
        public boolean supports(Class<?> authentication){
            return ApiAuthentication.class.isAssignableFrom(authentication);
        }


        private final Consumer<UserPrincipal> validAccount = userPrincipal -> {
                if(userPrincipal.isAccountNonLocked()){throw new LockedException("Your account is currently locked");}
                if(userPrincipal. isEnabled()){throw new DisabledException("Your account is currently disable");}
                if(userPrincipal.isCredentialsNonExpired()){throw new CredentialsExpiredException("Your password has expired. Please update your password");}
                if(userPrincipal.isAccountNonExpired()){throw new DisabledException("Your account has expired. Please contact your administrator");}

                };



}
