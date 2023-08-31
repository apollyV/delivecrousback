package com.imt.framework.web.tuto.resources;

import com.imt.framework.web.tuto.entities.Plat;
import com.imt.framework.web.tuto.repositories.PlatRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Path("/dishes")
public class PlatResource {

    @Autowired
    private PlatRepository platRepository;

    @GET
    @Produces("application/json")
    public List<Plat> getDishes(){
        return platRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    public void createDish(@NotNull @RequestBody Plat plat){
        platRepository.save(plat);
    }

    @PATCH
    @Consumes
    @Path("/{id}")
    public void updateDish(@NotNull @PathParam("id") Long id, @NotNull @RequestBody Plat plat) throws Exception {
        Optional<Plat> p = platRepository.findById(id);

        if(p.isEmpty()) {
            throw new Exception("Plat inconnu");
        }
        else {
            Plat dishToUpdate = p.get();
            dishToUpdate.setImage(plat.getImage());
            dishToUpdate.setTitre(plat.getTitre());
            dishToUpdate.setDescription(plat.getDescription());
            dishToUpdate.setPrix(plat.getPrix());
            dishToUpdate.setAllergene(plat.getAllergene());
            dishToUpdate.setCategory(plat.getCategory());

            platRepository.save(dishToUpdate);
        }
    }

    @DELETE
    @Path("/{id}")
    public void deleteDish(@NotNull @PathParam("id") final Long id){
        platRepository.deleteById(id);
    }
}
