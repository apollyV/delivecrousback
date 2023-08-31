package com.imt.framework.web.tuto.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantite;

    private String adresseLivraison;

    @OneToMany
    private List<Plat> plats;

    @ManyToOne
    private Utilisateur utilisateur;

    @Enumerated(EnumType.STRING)
    private EtatEnum etat;
}