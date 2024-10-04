package com.example.api.controller;

import com.example.api.dto.AuthDto;
import com.example.api.dto.UserDto;
import com.example.api.service.AuthService;
import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDto> login(@RequestBody UserDto userDto) throws AuthException, UnirestException {
        return ResponseEntity.ok(authService.login(userDto));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthDto> register(@RequestBody UserDto userDto) throws UnirestException {
        return ResponseEntity.ok(authService.register(userDto));
    }

}
