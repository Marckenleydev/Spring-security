package marc.dev.documentmanagementsystem.domain;

import lombok.Builder;
import marc.dev.documentmanagementsystem.dto.User;
import org.springframework.security.core.GrantedAuthority;
import io.jsonwebtoken.Claims;
import java.util.List;
@Builder
public class TokenData {
    private User user;
    private Claims claims;
    private boolean valid;
    private List<GrantedAuthority>  authorities;
}
