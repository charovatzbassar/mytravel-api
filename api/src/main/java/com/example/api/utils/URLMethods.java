package com.example.api.utils;

import java.util.Arrays;

public class URLMethods {
    public static String extractPathVariable(String requestURI, String pattern) {
        String[] parts = requestURI.split("/");
        System.out.println(parts.length);
        if (parts.length <= 3) return "";
        return pattern.equals(parts[2]) ? parts[3] : "";
    }
}
