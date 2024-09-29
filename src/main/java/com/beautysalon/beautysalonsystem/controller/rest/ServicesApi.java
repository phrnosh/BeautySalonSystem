package com.beautysalon.beautysalonsystem.controller.rest;

import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.model.entity.Salon;
import com.beautysalon.beautysalonsystem.model.entity.Services;
import com.beautysalon.beautysalonsystem.model.service.SalonService;
import com.beautysalon.beautysalonsystem.model.service.ServicesService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/services")
public class ServicesApi {

    @Inject
    private ServicesService servicesService;

    @Inject
    private SalonService salonService;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            Services servicesToRemove = servicesService.findById(id);

            if (servicesToRemove.isAvailable()) {
                log.error("Show is active on a cinema, can not remove show-id : "+ id);
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("Show is active on a cinema !!!")
                        .build();
            } else {
                servicesService.remove(id);
                for (Salon salon : salonService.findAll()) {
                    salon.getServicesList().removeIf(services -> services.getId().equals(servicesToRemove.getId()));
                    salonService.edit(salon);
                }
                log.info("services removed successfully-ID : " + id);
                return Response.accepted().build();
            }
        } catch (Exception e) {
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

            Object result = servicesService.findByServiceName(name);

            if (result != null) {
                log.info("services found-name : " + name);
                return Response.ok(result).build();
            } else {
                log.error("services not found-name : " + name);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for name: " + name)
                        .build();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }
}