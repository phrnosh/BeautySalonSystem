package com.beautysalon.beautysalonsystem.controller.rest;

import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.model.entity.Bank;
import com.beautysalon.beautysalonsystem.model.service.BankService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Path("/bank")
public class BankApi {
    @Inject
    private BankService bankService;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            bankService.remove(id);
            log.info("Bank removed successfully-ID : " + id);
            return Response.accepted().build();
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
            Bank bank = bankService.findByName(name.toUpperCase());
            if (bank != null) {
                log.info("Bank found successfully-name : " + name);
                return Response.ok(bank).build();
            } else {
                log.error("Bank not found-name : " + name);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("There Is No Bank With This Name" + name)
                        .build();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error : " + e.getMessage())
                    .build();

        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findByAccountNumber/{accountNumber}")
    public Response findByAccountNumber(@PathParam(value = "accountNumber") String accountNumber) {
        try {
            Bank bank = bankService.findByAccountNumber(accountNumber);

            if (bank != null) {
                log.info("Bank found-account number : " + accountNumber);
                return Response.ok(bank).build();
            } else {
                log.error("Bank not found-account number : " + accountNumber);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("There Is No Bank With This AccountNumber : " + accountNumber)
                        .build();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error : " + e.getMessage())
                    .build();

        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findByBranchCode/{branchCode}")
    public Response findByBranchCode(@PathParam(value = "branchCode") String branchCode) {
        try {
            Bank bank = bankService.findByBranchCode(Long.valueOf(branchCode));
            if (bank != null) {
                log.info("Bank found-branch code : " + branchCode);
                return Response.ok(bank).build();
            } else {
                log.error("Bank not found-branch code : " + branchCode);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("There Is No Bank With This BranchCode : " + branchCode)
                        .build();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error : " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findByBranchName/{branchName}")
    public Response findByBranchName(@PathParam(value = "branchName") String branchName) {
        try {
            Bank bank = bankService.findByBranchName(branchName.toUpperCase());
            if (bank != null) {
                log.info("Bank found-branch name: " + branchName);
                return Response.ok(bank).build();
            } else {
                log.error("Bank not found-branch name : " + branchName);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("There Is No Bank With This BranchName : " + branchName)
                        .build();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error : " + e.getMessage())
                    .build();
        }
    }
}