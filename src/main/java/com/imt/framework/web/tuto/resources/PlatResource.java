package com.imt.framework.web.tuto.resources;

import com.imt.framework.web.tuto.annotation.Authenticated;
import com.imt.framework.web.tuto.entities.Plat;
import com.imt.framework.web.tuto.repositories.PlatRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Path("/dishes")
@Authenticated
@CrossOrigin(origins = "*")
public class PlatResource {

    @Autowired
    private PlatRepository platRepository;

    @GET
    @Produces("application/json")
    public List<Plat> getDishes(){
        return platRepository.findAll();
    }

}
