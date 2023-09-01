package com.imt.framework.web.tuto.resources;

import com.imt.framework.web.tuto.annotation.Authenticated;
import com.imt.framework.web.tuto.entities.Commande;
import com.imt.framework.web.tuto.repositories.CommandeRepository;
import com.imt.framework.web.tuto.repositories.UtilisateurRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Path("/orders")
@Authenticated
public class CommandeResource {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GET
    @Produces("application/json")
    @Authenticated
    public List<Commande> getOrders(){
        return commandeRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    public void createOrder(@NotNull @RequestBody Commande commande){
        commandeRepository.save(commande);
    }

    @PATCH
    @Consumes
    @Path("/{id}")
    public void updateOrder(@NotNull @PathParam("id") Long id, @NotNull @RequestBody Commande commande) throws Exception {
        Optional<Commande> c = commandeRepository.findById(id);

        if(c.isEmpty()) {
            throw new Exception("Commande inconnue");
        }
        else {
            Commande orderToUpdate = c.get();
            orderToUpdate.setQuantite(commande.getQuantite());
            orderToUpdate.setAdresseLivraison(commande.getAdresseLivraison());
            orderToUpdate.setPlats(commande.getPlats());
            orderToUpdate.setUtilisateur(commande.getUtilisateur());
            orderToUpdate.setEtat(commande.getEtat());

            commandeRepository.save(orderToUpdate);
        }
    }

    @DELETE
    @Path("/{id}")
    public void deleteOrder(@NotNull @PathParam("id") final Long id){
        commandeRepository.deleteById(id);
    }

}
