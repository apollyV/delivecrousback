package com.imt.framework.web.tuto.repositories;

import com.imt.framework.web.tuto.entities.AllergeneEnum;
import com.imt.framework.web.tuto.entities.CategorieEnum;
import com.imt.framework.web.tuto.entities.Plat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlatRepository extends JpaRepository<Plat, Long> {

        // Trouver des plats par catégorie
        List<Plat> findByCategory(CategorieEnum category);

        // Trouver des plats par allergène
        List<Plat> findByAllergene(AllergeneEnum allergene);

        // Rechercher des plats par titre ou description
        List<Plat> findByTitreContainingOrDescriptionContaining(String titre, String description);
        }
