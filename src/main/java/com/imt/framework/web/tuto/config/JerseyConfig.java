package com.imt.framework.web.tuto.config;

import com.imt.framework.web.tuto.filter.AuthenticationFilter;
import com.imt.framework.web.tuto.resources.TokenResource;
import com.imt.framework.web.tuto.resources.UtilisateurResource;
import com.imt.framework.web.tuto.resources.PlatResource;
import com.imt.framework.web.tuto.resources.CommandeResource;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("delivecrous")
@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig(){
        register(UtilisateurResource.class);
        register(PlatResource.class);
        register(CommandeResource.class);
        register(TokenResource.class);
        register(AuthenticationFilter.class);
    }
}
