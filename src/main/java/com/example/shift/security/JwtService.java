package com.example.shift.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.UUID;
@Service
public interface JwtService {
    String generateJwt(UUID id);
    String generateRefreshJwt(UUID id);
    SecretKey singnwithkey();
    Jws<Claims> extractJwt(String jwt);
    ResponseEntity<?> refreshToken(String refreshToken);
}