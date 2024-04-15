package marc.dev.documentmanagementsystem.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import marc.dev.documentmanagementsystem.domain.Token;
import marc.dev.documentmanagementsystem.domain.TokenData;
import marc.dev.documentmanagementsystem.dto.User;
import marc.dev.documentmanagementsystem.enumaration.TokenType;

import java.util.Optional;
import java.util.function.Function;

public interface JwtService {

    String createToken(User user, Function<Token, String> tokenFunction);
    Optional <String> extractToken(HttpServletRequest request, String tokenType);
    void addCookie(HttpServletResponse response, User user, TokenType type);
    <T> T getTokenData(String token, Function<TokenData, T> tokenFunction);
    void removeCookie(HttpServletRequest request, HttpServletResponse response, String CookieName);
}
