package com.imt.framework.web.tuto.data;

import com.imt.framework.web.tuto.entities.AllergeneEnum;
import com.imt.framework.web.tuto.entities.CategorieEnum;
import com.imt.framework.web.tuto.entities.Plat;
import com.imt.framework.web.tuto.repositories.PlatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {
    @Bean
    public CommandLineRunner initDatabase(PlatRepository platRepository) {
        return args -> {
            platRepository.save(new Plat(null,"https://images.unsplash.com/photo-1598023696416-0193a0bcd302?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1872&q=80", "Pizza Margherita", "Une délicieuse pizza margherita", 10.5, AllergeneEnum.GLUTEN, CategorieEnum.PROTEINE));
            platRepository.save(new Plat(null,"https://images.unsplash.com/photo-1551248429-40975aa4de74?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1890&q=80", "Salade César", "Salade fraîche avec du poulet grillé", 8.5, AllergeneEnum.OEUF, CategorieEnum.VEGETARIEN));
            platRepository.save(new Plat(null,"https://images.unsplash.com/photo-1520072959219-c595dc870360?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1890&q=80", "Burger Végétarien", "Burger sans viande avec des légumes frais", 9.0, AllergeneEnum.GLUTEN, CategorieEnum.VEGETARIEN));
            platRepository.save(new Plat(null,"https://images.unsplash.com/photo-1608756687911-aa1599ab3bd9?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1887&q=80", "Spaghetti Carbonara", "Pâtes crémeuses avec du bacon", 11.0, AllergeneEnum.OEUF, CategorieEnum.GRAS));
            platRepository.save(new Plat(null,"https://images.unsplash.com/photo-1588166524941-3bf61a9c41db?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1884&q=80", "Tofu Grillé", "Tofu mariné et grillé", 7.5, AllergeneEnum.GLUTEN, CategorieEnum.VEGETARIEN));
            platRepository.save(new Plat(null,"https://images.unsplash.com/photo-1609183480237-ccbb2d7c5772?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80", "Poulet Teriyaki", "Poulet grillé avec sauce teriyaki", 12.0, AllergeneEnum.GLUTEN, CategorieEnum.PROTEINE));
            platRepository.save(new Plat(null,"https://images.unsplash.com/photo-1595908129746-57ca1a63dd4d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1887&q=80", "Risotto aux Champignons", "Risotto crémeux aux champignons", 10.0, AllergeneEnum.GLUTEN, CategorieEnum.GRAS));
            platRepository.save(new Plat(null,"https://images.unsplash.com/photo-1601000938365-f182c5ec2f77?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80", "Tarte aux Noix", "Tarte sucrée aux noix", 6.5, AllergeneEnum.ARACHIDE, CategorieEnum.GRAS));
            platRepository.save(new Plat(null,"https://images.unsplash.com/photo-1593001872095-7d5b3868fb1d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80", "Falafels", "Boulettes de pois chiches épicées", 7.0, AllergeneEnum.GLUTEN, CategorieEnum.VEGETARIEN));
            platRepository.save(new Plat(null,"https://images.unsplash.com/photo-1508615263227-c5d58c1e5821?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1935&q=80", "Steak Grillé", "Steak juteux grillé à la perfection", 15.0, AllergeneEnum.GLUTEN, CategorieEnum.PROTEINE));
        };
    }
}
