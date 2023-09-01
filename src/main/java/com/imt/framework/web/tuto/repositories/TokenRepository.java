package com.imt.framework.web.tuto.repositories;

import com.imt.framework.web.tuto.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    // Trouver un token par sa valeur
    Optional<Token> findByTokenValue(String encodedToken);

    // Supprime les tokens périmés
    @Transactional
    @Modifying
    @Query("DELETE FROM Token t WHERE t.expiration < CURRENT_DATE")
    void deleteExpiredTokens();

    List<Token> findAll();
}
