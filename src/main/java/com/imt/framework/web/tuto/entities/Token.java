package com.imt.framework.web.tuto.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tokenValue;

    private LocalDate expiration;

    @ManyToOne
    private Utilisateur utilisateur;
}
