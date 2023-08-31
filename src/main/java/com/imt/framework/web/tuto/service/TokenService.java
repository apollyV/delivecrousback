package com.imt.framework.web.tuto.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TokenService {

    private static Set<String> validTokens = new HashSet<>();

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String generateNewToken() {
        String token = UUID.randomUUID().toString();
        validTokens.add(passwordEncoder.encode(token));
        return token;
    }

    public static boolean isValid(String token) {
        return validTokens.contains(passwordEncoder.encode(token));
    }

    public static void removeToken(String token) {
        validTokens.remove(token);
    }
}

