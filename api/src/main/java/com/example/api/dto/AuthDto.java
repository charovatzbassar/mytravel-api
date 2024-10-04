package com.example.api.dto;

public class AuthDto {
    private String authToken;
    private String accessToken;

    public AuthDto(String authToken, String accessToken) {
        this.authToken = authToken;
        this.accessToken = accessToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "AuthDto{" +
                "authToken='" + authToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
