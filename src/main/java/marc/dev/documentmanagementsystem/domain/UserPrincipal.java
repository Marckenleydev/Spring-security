package marc.dev.documentmanagementsystem.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import marc.dev.documentmanagementsystem.dto.User;
import marc.dev.documentmanagementsystem.entity.CredentialEntity;
import marc.dev.documentmanagementsystem.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    @Getter
    private  final User user;
    private final CredentialEntity credentialEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities());
    }

    @Override
    public String getPassword() {
        return credentialEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {

        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialNonExpired();
    }

    @Override
    public boolean isEnabled() {

        return user.isEnabled();
    }
}
