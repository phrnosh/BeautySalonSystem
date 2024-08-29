package com.beautysalon.beautysalonsystem.controller.rest;

import com.beautysalon.beautysalonsystem.model.entity.Manager;
import com.beautysalon.beautysalonsystem.model.service.ManagerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/manager")
public class ManagerApi {
    @Inject
    private ManagerService managerService;

    @PUT
    // media type convert error
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPerson(Manager manager) {
        try {
            System.out.println("PERSON API EDIT : " + manager.toString());

            managerService.edit(manager);
            return Response.ok().entity("salam").build();
        }catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") Long id) {
        try {
            System.out.println("PERSON API DELETE : " + id);
            managerService.remove(id);
            return Response.accepted().build();
        }catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
