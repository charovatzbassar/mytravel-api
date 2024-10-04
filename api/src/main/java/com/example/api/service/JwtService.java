package com.example.api.service;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.example.api.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Service
public class JwtService {
    @Value("${spring.application.jwt_secret}")
    private String jwtSecret;

    public String generateToken(User user) {
        try {
            long expirationTime = 864_000_00;
            return JWT.create()
                    .withClaim("username", user.getUsername())
                    .withClaim("email", user.getEmail())
                    .withClaim("user_id", user.getUserId())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                    .sign(Algorithm.HMAC256(jwtSecret));
        } catch (JWTCreationException e) {
            throw new RuntimeException("Could not create token!");
        }
    }

    public Map<String, Claim> validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (Exception e) {
            throw new RuntimeException("JWT invalid!");
        }
    }


    public DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }
}


