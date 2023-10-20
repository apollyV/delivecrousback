package com.imt.framework.web.tuto.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adresseLivraison;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "commande_plats",
            joinColumns = @JoinColumn(name = "commande_id"),
            inverseJoinColumns = @JoinColumn(name = "plat_id")
    )
    private List<Plat> plats;

    @ManyToOne
    private Utilisateur utilisateur;

    @Enumerated(EnumType.STRING)
    private EtatEnum etat;
}