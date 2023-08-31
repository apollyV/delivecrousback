package com.imt.framework.web.tuto.repositories;

import com.imt.framework.web.tuto.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    // Trouver un utilisateur par son nom d'utilisateur
    Optional<Utilisateur> findByUsername(String username);

    // Vérifier si un nom d'utilisateur existe déjà
    boolean existsByUsername(String username);
}
