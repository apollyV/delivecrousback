package com.imt.framework.web.tuto.resources;

import com.imt.framework.web.tuto.entities.Utilisateur;
import com.imt.framework.web.tuto.repositories.UtilisateurRepository;
import com.imt.framework.web.tuto.service.TokenService;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Path("/users")
public class UtilisateurResource {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private TokenService tokenService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @GET
    @Produces("application/json")
    public List<Utilisateur> getUsers(){
        return utilisateurRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    public void createUser(@NotNull @RequestBody Utilisateur utilisateur){
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateurRepository.save(utilisateur);
    }

    @PATCH
    @Consumes
    @Path("/{id}")
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
            return tokenService.generateNewToken();
        }
        return null;
    }
}
