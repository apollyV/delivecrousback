package com.imt.framework.web.tuto.resources;

import com.imt.framework.web.tuto.entities.Token;
import com.imt.framework.web.tuto.repositories.TokenRepository;
import jakarta.ws.rs.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Path("/token")
public class TokenResource {

    @Autowired
    private TokenRepository tokenRepository;


    @GET
    @Path("/all")
    @Produces("application/json")
    public List<Token> getAllToken() {
        return tokenRepository.findAll();
    }
}
