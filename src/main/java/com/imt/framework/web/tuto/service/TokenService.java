package com.imt.framework.web.tuto.service;

import com.imt.framework.web.tuto.entities.Token;
import com.imt.framework.web.tuto.entities.Utilisateur;
import com.imt.framework.web.tuto.repositories.TokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;


@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Génère un Token, il est renvoyé au client, et encodé puis stocké dans la base
    public String createToken(Utilisateur user) {
        String value = UUID.randomUUID().toString();
        Token token = new Token();
        token.setTokenValue(passwordEncoder.encode(value));
        token.setExpiration(LocalDate.now().plusDays(10));
        token.setUtilisateur(user);
        tokenRepository.save(token);
        return value;
    }

    // Lance la fonction de nettoyage des token perimé toutes les 5 minutes
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void cleanUpExpiredTokens() {
        tokenRepository.deleteExpiredTokens();
    }
}
