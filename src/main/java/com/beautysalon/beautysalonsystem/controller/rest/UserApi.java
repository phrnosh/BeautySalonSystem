package com.beautysalon.beautysalonsystem.controller.rest;


import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.model.entity.User;
import com.beautysalon.beautysalonsystem.model.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/user")
public class UserApi {


    @Inject
    private UserService userService;

    @GET
    @Path("/{username}")
    @Produces("text/plain")
    public Response resetPassword(@PathParam("username") String username) {
        try {

            if (userService.findByUsername(username) != null) {
                User user = (User) userService.findByUsername(username);
                int newPassword = 1000 + (int) (Math.random()*8999);
                user.setPassword(String.valueOf(newPassword));
                userService.edit(user);
                log.info("Reset password for user " + username);
                return Response.ok(newPassword).build();
            } else {
                log.error("User " + username + " not found for resetting password");
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findUserByUsername/{username}")
    public Response findByUsername(@PathParam(value = "username") String username) {
        try {

            Object result = userService.findByUsername(username);

            if (result != null) {
                log.info("User found-username " + username);
                return Response.ok(result).build();
            } else {
                log.error("User not found-username " + username);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for name: " + username)
                        .build();
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
    @Path("/findByRole/{role}")
    public Response findByRole(@PathParam(value = "role") String role) {
        try {

            Object result = userService.findByRole(role);

            if (result != null) {
                log.info("User found-role " + role);
                return Response.ok(result).build();
            } else {
                log.error("User not found-role " + role);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for role: " + role)
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