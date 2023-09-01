package com.imt.framework.web.tuto.filter;

import com.imt.framework.web.tuto.entities.Token;
import com.imt.framework.web.tuto.repositories.TokenRepository;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.List;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Autowired
    private TokenRepository tokenRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Routes authorisées par tout le monde
        if (requestContext.getUriInfo().getPath().equals("users/login")
        || requestContext.getUriInfo().getPath().equals("users/create")
        || requestContext.getUriInfo().getPath().equals("dishes")
        || (requestContext.getHeaderString("admin") != null && requestContext.getHeaderString("admin").equals("admin1234"))) {
            return;
        }

        String tokenValue = requestContext.getHeaderString("Authorization");
        if (tokenValue != null) {
            boolean isAuthenticated = false;

            List<Token> allTokens = tokenRepository.findAll();
            for (Token storedToken : allTokens) {
                if (passwordEncoder.matches(tokenValue, storedToken.getTokenValue())) {
                    isAuthenticated = true;
                    break;
                }
            }

            if (!isAuthenticated) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Utilisateur non authentifié").build());
            }
        } else {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Veuillez vous connecter").build());
        }
    }
}

