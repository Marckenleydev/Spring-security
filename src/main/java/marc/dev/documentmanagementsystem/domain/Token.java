package marc.dev.documentmanagementsystem.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Token {
    private String accessToken;
    private String refreshToken;
}
