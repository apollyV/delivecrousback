package com.imt.framework.web.tuto.resources;

import com.imt.framework.web.tuto.annotation.Authenticated;
import com.imt.framework.web.tuto.entities.Token;
import com.imt.framework.web.tuto.entities.Utilisateur;
import com.imt.framework.web.tuto.repositories.TokenRepository;
import com.imt.framework.web.tuto.repositories.UtilisateurRepository;
import com.imt.framework.web.tuto.service.TokenService;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Path("/users")
public class UtilisateurResource {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenRepository tokenRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @GET
    @Produces("application/json")
    public List<Utilisateur> getUsers(){
        return utilisateurRepository.findAll();
    }

    @POST
    @Path("/create")
    @Consumes("application/json")
    public Response createUser(@NotNull @RequestBody Utilisateur utilisateur) {
        if(utilisateurRepository.existsByUsername(utilisateur.getUsername())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Le nom d'utilisateur " + utilisateur.getUsername() + " est déjà utilisé").build();
        }
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateurRepository.save(utilisateur);
        return Response.status(Response.Status.OK).entity("Votre compte a bien été créé").build();
    }

    @PATCH
    @Consumes
    @Path("/{id}")
    @Authenticated
    public void updateUser(@NotNull @PathParam("id") Long id, @NotNull @RequestBody Utilisateur utilisateur) throws Exception {
        Optional<Utilisateur> u = utilisateurRepository.findById(id);

        if(u.isEmpty()) {
            throw new Exception("Utilisateur inconnu");
        }
        else {
            Utilisateur userToUpdate = u.get();
            userToUpdate.setNom(utilisateur.getNom());
            userToUpdate.setPrenom(utilisateur.getPrenom());
            userToUpdate.setUsername(utilisateur.getUsername());
            // Si le mot de passe est fourni dans la mise à jour, le hasher avant de l'enregistrer
            if(utilisateur.getPassword() != null && !utilisateur.getPassword().isEmpty()) {
                userToUpdate.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
            }
            userToUpdate.setSolde(utilisateur.getSolde());

            utilisateurRepository.save(userToUpdate);
        }
    }

    @DELETE
    @Path("/{id}")
    @Authenticated
    public void deleteUser(@NotNull @PathParam("id") final Long id){
        utilisateurRepository.deleteById(id);
    }

    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public String loginUser(@NotNull @RequestBody Utilisateur user) {
        Optional<Utilisateur> existingUser = utilisateurRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            return tokenService.createToken(existingUser.get());
        }
        return null;
    }

    // Vérifie si l'utilisateur est bien connecté en utilisant le token et si sa date d'expiration est valide, et mets à jour la date d'expiration
    @GET
    @Path("/verify")
    @Produces("application/json")
    public boolean verifyToken(@HeaderParam("Authorization") String tokenValue) {
        List<Token> allTokens = tokenRepository.findAll();

        for (Token storedToken : allTokens) {
            if (passwordEncoder.matches(tokenValue, storedToken.getTokenValue())) {
                if (storedToken.getExpiration().isAfter(LocalDate.now())) {
                    storedToken.setExpiration(LocalDate.now().plusDays(10));
                    tokenRepository.save(storedToken);
                    return true;
                }
                break;
            }
        }
        return false;
    }

    @DELETE
    @Path("/logout")
    public Response logoutUser(@HeaderParam("Authorization") String tokenValue) {
        // Trouver le token hashé correspondant dans la base de données
        List<Token> allTokens = tokenRepository.findAll();

        for (Token storedToken : allTokens) {
            if (passwordEncoder.matches(tokenValue, storedToken.getTokenValue())) {
                // Supprimer le token de la base de données
                tokenRepository.delete(storedToken);
                return Response.status(Response.Status.OK).entity("Déconnexion réussie").build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Token non trouvé").build();
    }


}
