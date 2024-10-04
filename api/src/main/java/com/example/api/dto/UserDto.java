package com.example.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDto {
    @NotNull
    @NotBlank(message = "Username cannot be blank!")
    private String username;

    @NotNull
    @NotBlank(message = "Email cannot be blank!")
    @Email
    private String email;

    @NotNull
    @NotBlank(message = "Password cannot be blank!")
    private String password;

    public UserDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public @NotNull @NotBlank(message = "Username cannot be blank!") String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @NotBlank(message = "Username cannot be blank!") String username) {
        this.username = username;
    }

    public @NotNull @NotBlank(message = "Email cannot be blank!") @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @NotBlank(message = "Email cannot be blank!") @Email String email) {
        this.email = email;
    }

    public @NotNull @NotBlank(message = "Password cannot be blank!") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @NotBlank(message = "Password cannot be blank!") String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
