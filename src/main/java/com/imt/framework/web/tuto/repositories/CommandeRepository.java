package com.imt.framework.web.tuto.repositories;

import com.imt.framework.web.tuto.entities.Commande;
import com.imt.framework.web.tuto.entities.EtatEnum;
import com.imt.framework.web.tuto.entities.Plat;
import com.imt.framework.web.tuto.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    // Trouver toutes les commandes d'un utilisateur spécifique
    List<Commande> findByUtilisateur(Utilisateur utilisateur);

    // Trouver toutes les commandes dans un état spécifique
    List<Commande> findByEtat(EtatEnum etat);

    // Trouver toutes les commandes pour un plat spécifique
    @Query("SELECT c FROM Commande c JOIN c.plats p WHERE p.id = :platId")
    List<Commande> findByPlatId(@Param("platId") Long platId);

    @Transactional
    default void removePlatFromCommande(Long commandeId, Plat platToRemove) {
        findById(commandeId).ifPresent(commande -> {
            commande.getPlats().remove(platToRemove);
        });
    }
}