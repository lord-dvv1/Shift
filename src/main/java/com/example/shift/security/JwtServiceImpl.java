// src/main/java/com/example/shift/security/JwtServiceImpl.java
package com.example.shift.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import javax.crypto.SecretKey; // Keep javax.crypto for now if it compiles
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID; // <--- Import UUID

@Service
public class JwtServiceImpl implements JwtService {


    private static final String SECRET_KEY_BASE64 = Base64.getEncoder().encodeToString("nimabolgandahamfaqatvafaqatolgailoveprogfra".getBytes());

    // Token expiration times
    private static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 5;
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 30;
    private final SecretKey signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY_BASE64));

    private SecretKey getSigningKey() {
        return signingKey;
    }

    @Override
    public String generateJwt(UUID id) {
        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String generateRefreshJwt(UUID id) {
        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public SecretKey singnwithkey() {
        return getSigningKey();
    }

    @Override
    public Jws<Claims> extractJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt);
    }

    @Override
    public ResponseEntity<?> refreshToken(String refreshToken) {
        Jws<Claims> claims = extractJwt(refreshToken);
        String subject = claims.getBody().getSubject();
        UUID userId = UUID.fromString(subject);
        String newAccess = generateJwt(userId);
        return ResponseEntity.ok(Map.of("accessToken", newAccess));
    }
}