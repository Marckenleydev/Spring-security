package marc.dev.documentmanagementsystem.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;


@Data
public class JwtConfiguration {
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("{jwt.secret}")
    private String secret;

}
