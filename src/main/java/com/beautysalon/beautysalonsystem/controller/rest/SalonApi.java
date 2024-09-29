package com.beautysalon.beautysalonsystem.controller.rest;

import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.model.entity.Manager;
import com.beautysalon.beautysalonsystem.model.entity.Salon;
import com.beautysalon.beautysalonsystem.model.entity.SalonVO;
import com.beautysalon.beautysalonsystem.model.service.ManagerService;
import com.beautysalon.beautysalonsystem.model.service.SalonService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/salon")
public class SalonApi {

    @Inject
    private SalonService salonService;

    @Inject
    private ManagerService managerService;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            salonService.remove(id);
            Manager manager = managerService.findManagerBySalonId(id);
            manager.setSalon(null);
            managerService.edit(manager);
            log.info("Salon removed successfully-ID : " + id);
            return Response.accepted().build();
        }catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findByName/{name}")
    public Response findByName(@PathParam(value = "name") String name) {
        try {

            Salon salon = salonService.findByName(name);

            if (salon != null) {
                SalonVO salonVO = new SalonVO(salon);
                log.info("Salon found successfully-name : " + name);
                return Response.ok(salonVO).build();
            } else {
                log.error("Salon not found-name : " + name);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for name: " + name)
                        .build();
            }
        }catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }
}