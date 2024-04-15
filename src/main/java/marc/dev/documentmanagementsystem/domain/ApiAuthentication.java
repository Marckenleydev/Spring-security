package marc.dev.documentmanagementsystem.domain;

import marc.dev.documentmanagementsystem.dto.User;
import marc.dev.documentmanagementsystem.exception.ApiException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import java.util.Collection;

public class ApiAuthentication extends AbstractAuthenticationToken {
    private static final String PASSWORD_PROTECTED = "[PASSWORD PROTECTED]";
    private static final String EMAIL_PROTECTED = "[EMAIL PROTECTED]";
    private User user;
    private final String email;
    private final String password;
    private final boolean authenticated;




    public ApiAuthentication(String email,String password){
        super(AuthorityUtils.NO_AUTHORITIES);
        this.email = email;
        this.password = password;
        this.authenticated = false;
    }
    private ApiAuthentication(User user,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.password = PASSWORD_PROTECTED;
        this.email = EMAIL_PROTECTED;
        this.authenticated = true;
    }

    public static ApiAuthentication unAuthenticated(String email,String password){
       return new ApiAuthentication(email,password);
    }

    public static ApiAuthentication authenticated(User user,Collection<? extends GrantedAuthority> authorities) {
        return new ApiAuthentication(user, authorities);
    }
    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new ApiException("You cannot set authentication");
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }
    @Override
    public Object getCredentials() {
        return PASSWORD_PROTECTED;
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }


    public String getPassword(){
        return this.password;
    }

    public String getEmail(){
        return this.email;
    }
}
