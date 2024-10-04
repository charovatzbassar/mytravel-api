package com.example.api.controller;

import com.example.api.dto.UserDto;
import com.example.api.entity.User;
import com.example.api.service.EmailService;
import com.example.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<User> create(@Valid @RequestBody UserDto data) {
        return ResponseEntity.ok(userService.create(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@Valid @RequestBody UserDto data, @PathVariable Long id) {
        emailService.sendSimpleMessage(data.getEmail(), "Your account was updated", "Hello, \nyour account has been updated.");
        return ResponseEntity.ok(userService.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) {
        emailService.sendSimpleMessage(userService.getById(id).getEmail(), "Sorry to see you go :(", "Hello, \nyour account has been deleted.");
        return ResponseEntity.ok(userService.delete(id));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<Boolean> verifyUser(@PathVariable Long id) {
        try {
            userService.verifyUser(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
