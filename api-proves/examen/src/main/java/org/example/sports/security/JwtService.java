package org.example.sports.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.sports.domain.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret}")
    private String secretKey;

    @Value("${application.security.jwt.expiration-seconds:300}")
    private long expirationSeconds;

    @Value("${application.security.jwt.issuer:sports-api}")
    private String issuer;

    public String generateToken(AppUser user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationSeconds * 1000);

        List<String> roles = user.rolesList();

        return Jwts.builder()
                .issuer(issuer)
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(exp)
                .claim("roles", roles)
                .signWith(getSignInKey())
                .compact();
    }

    public JwtPrincipal parseAndValidate(String token) throws JwtException {
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String tokenIssuer = claims.getIssuer();
        if (tokenIssuer == null || !tokenIssuer.equals(issuer)) {
            throw new JwtException("Invalid issuer");
        }

        String email = claims.getSubject();
        if (email == null || email.isBlank()) {
            throw new JwtException("Missing subject");
        }

        List<?> rawRoles = claims.get("roles", List.class);
        List<String> roles = (rawRoles == null)
                ? List.of()
                : rawRoles.stream().map(String::valueOf).toList();

        return new JwtPrincipal(email, roles);
    }

    private SecretKey getSignInKey() {
        // HS256 needs >= 32 bytes
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
