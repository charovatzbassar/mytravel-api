package com.example.api.service;

import com.example.api.dto.AuthDto;
import com.example.api.dto.UserDto;
import com.example.api.entity.User;
import com.example.api.exception.EntityNotFoundException;
import com.example.api.utils.HashMethods;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Value("${auth0.token.url}")
    private String tokenUrl;

    @Value("${auth0.token.client-id}")
    private String clientId;

    @Value("${auth0.token.client-secret}")
    private String clientSecret;

    @Value("${okta.oauth2.audience}")
    private String tokenAudience;

    @Autowired
    public AuthService(UserService userService, JwtService jwtService, EmailService emailService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public AuthDto register(UserDto userDto) throws UnirestException {
        User user = userService.getByEmail(userDto.getEmail());

        if (user != null) throw new RuntimeException("User already exists!");

        userDto.setPassword(HashMethods.hashString(userDto.getPassword()));

        userService.create(userDto);

        User existingUser = userService.getByEmail(userDto.getEmail());

        emailService.sendTemplateMessage(existingUser.getEmail(), "Welcome to MyTravel!", existingUser.getUsername());


        HttpResponse<String> response = Unirest.post(tokenUrl)
                .header("content-type", "application/json")
                .body("{\"client_id\":\"" + clientId + "\",\"client_secret\":\"" + clientSecret + "\",\"audience\":\"" + tokenAudience + "\",\"grant_type\":\"client_credentials\"}")
                .asString();


        String accessToken = response.getBody().split("\"")[3];

        String authToken = jwtService.generateToken(existingUser);

        return new AuthDto(authToken, accessToken);
    }

    public AuthDto login(UserDto userDto) throws AuthException, UnirestException {
        User user = userService.getByEmail(userDto.getEmail());

        if (user == null) throw new EntityNotFoundException("User");

        if (!Objects.equals(HashMethods.hashString(userDto.getPassword()), user.getPasswordHash())) throw new AuthException("Password is incorrect!");

        HttpResponse<String> response = Unirest.post(tokenUrl)
                .header("content-type", "application/json")
                .body("{\"client_id\":\"" + clientId + "\",\"client_secret\":\"" + clientSecret + "\",\"audience\":\"" + tokenAudience + "\",\"grant_type\":\"client_credentials\"}")
                .asString();

        String accessToken = response.getBody().split("\"")[3];

        String authToken = jwtService.generateToken(user);

        return new AuthDto(authToken, accessToken);
    }
}
