package com.imt.framework.web.tuto.resources;

import com.imt.framework.web.tuto.annotation.Authenticated;
import com.imt.framework.web.tuto.entities.Commande;
import com.imt.framework.web.tuto.entities.EtatEnum;
import com.imt.framework.web.tuto.entities.Plat;
import com.imt.framework.web.tuto.entities.Utilisateur;
import com.imt.framework.web.tuto.repositories.CommandeRepository;
import com.imt.framework.web.tuto.repositories.UtilisateurRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/orders")
@Authenticated
public class CommandeResource {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilisateurResource utilisateurResource;

    @GET
    @Produces("application/json")
    public List<Commande> getOrders(){
        return commandeRepository.findAll();
    }

    @GET
    @Produces("application/json")
    @Path("/utilisateurConnecte")
    public Commande getCommandeByUtilisateurConnecte( @HeaderParam("Authorization") String tokenValue ) {
        Utilisateur utilisateur = this.utilisateurResource.verifyToken(tokenValue);
        List<Commande> commandesEnCours = commandeRepository.findByUtilisateur(utilisateur)
                .stream()
                .filter(commande -> commande.getEtat().equals(EtatEnum.CHOIX_EN_COURS))
                .toList();
        if ( commandesEnCours.isEmpty() )
            return null;
        return commandesEnCours.get(0);
    }

    @PATCH
    @Consumes
    public Response addPlat( @NotNull @RequestBody Plat plat, @HeaderParam("Authorization") String tokenValue ) {
        Utilisateur utilisateur = this.utilisateurResource.verifyToken(tokenValue);
        List<Commande> commandesEnCours = commandeRepository.findByUtilisateur(utilisateur)
                                                .stream()
                                                .filter(commande -> commande.getEtat().equals(EtatEnum.CHOIX_EN_COURS))
                                                .toList();
        if (commandesEnCours.isEmpty()) {
            Commande nouvelleCommande = new Commande();
            List<Plat> plats = nouvelleCommande.getPlats();
            plats.add(plat);
            nouvelleCommande.setPlats(plats);
            nouvelleCommande.setEtat(EtatEnum.CHOIX_EN_COURS);
            nouvelleCommande.setUtilisateur(utilisateur);
            commandeRepository.save(nouvelleCommande);
        } else {
            Commande orderToUpdate = commandesEnCours.get(0);
            List<Plat> plats = orderToUpdate.getPlats();
            plats.add(plat);
            orderToUpdate.setPlats(plats);
            commandeRepository.save(orderToUpdate);
        }
        return Response.status(Response.Status.OK).entity("Le plat a été ajouté").build();
    }

    @PATCH
    @Consumes
    @Path("/finalizeWithAdress")
    public Response finalizeOrder( @NotNull @RequestBody String adresse, @HeaderParam("Authorization") String tokenValue ) {
        Utilisateur utilisateur = this.utilisateurResource.verifyToken(tokenValue);
        List<Commande> commandesEnCours = commandeRepository.findByUtilisateur(utilisateur)
                .stream()
                .filter(commande -> commande.getEtat().equals(EtatEnum.CHOIX_EN_COURS))
                .toList();

        if(commandesEnCours.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("La commande n'existe pas").build();
        }
        else {
            Commande orderToUpdate = commandesEnCours.get(0);
            orderToUpdate.setAdresseLivraison(adresse);
            orderToUpdate.setEtat(EtatEnum.TRAITEMENT_EN_COURS);
            commandeRepository.save(orderToUpdate);
            return Response.status(Response.Status.OK).entity("La commande a été finalisée, chaud devant !").build();
        }
    }

    @PATCH
    @Consumes
    @Path("finalize")
    public Response finalizeOrder( @HeaderParam("Authorization") String tokenValue ) {
        Utilisateur utilisateur = this.utilisateurResource.verifyToken(tokenValue);
        List<Commande> commandesEnCours = commandeRepository.findByUtilisateur(utilisateur)
                .stream()
                .filter(commande -> commande.getEtat().equals(EtatEnum.CHOIX_EN_COURS))
                .toList();

        if(commandesEnCours.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("La commande n'existe pas").build();
        }
        else {
            Commande orderToUpdate = commandesEnCours.get(0);
            orderToUpdate.setAdresseLivraison(orderToUpdate.getUtilisateur().getAdresse());
            commandeRepository.save(orderToUpdate);
            return Response.status(Response.Status.OK).entity("La commande a été finalisée, chaud devant !").build();
        }
    }

    @DELETE
    @Path("/delete")
    public Response deleteOrder( @HeaderParam("Authorization") String tokenValue ){
        Utilisateur utilisateur = this.utilisateurResource.verifyToken(tokenValue);
        List<Commande> commandesEnCours = commandeRepository.findByUtilisateur(utilisateur)
                .stream()
                .filter(commande -> commande.getEtat().equals(EtatEnum.CHOIX_EN_COURS))
                .toList();
        if(commandesEnCours.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("La commande n'existe pas").build();
        } else {
            Commande orderToDelete = commandesEnCours.get(0);
            this.commandeRepository.delete(orderToDelete);
            return Response.status(Response.Status.OK).entity("La commande a été supprimée avec succès !").build();
        }
    }

    @DELETE
    @Path("/deletePlat")
    public Response deletePlat( @NotNull @RequestBody Plat plat, @HeaderParam("Authorization") String tokenValue ){
        Utilisateur utilisateur = this.utilisateurResource.verifyToken(tokenValue);
        List<Commande> commandesEnCours = commandeRepository.findByUtilisateur(utilisateur)
                .stream()
                .filter(commande -> commande.getEtat().equals(EtatEnum.CHOIX_EN_COURS))
                .toList();
        if(commandesEnCours.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("La commande n'existe pas").build();
        } else if ( !commandesEnCours.get(0).getPlats().contains(plat) ) {
            return Response.status(Response.Status.NOT_FOUND).entity("Le plat que vous tentez de supprimer n'est pas inclus dans la commande").build();
        } else {
            this.commandeRepository.removePlatFromCommande(commandesEnCours.get(0).getId(), plat);
            return Response.status(Response.Status.OK).entity("Le plat a été retiré avec succès !").build();
        }
    }

}
