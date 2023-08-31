package com.imt.framework.web.tuto.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class Plat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String titre;
    private String description;
    private Double prix;
    @Enumerated(EnumType.STRING)
    private AllergeneEnum allergene;
    @Enumerated(EnumType.STRING)
    private CategorieEnum category;
}
