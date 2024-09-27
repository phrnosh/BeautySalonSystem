package com.beautysalon.beautysalonsystem.controller.rest;


import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.model.entity.Customer;
import com.beautysalon.beautysalonsystem.model.entity.CustomerVO;
import com.beautysalon.beautysalonsystem.model.service.CustomerService;
import com.beautysalon.beautysalonsystem.model.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Path("/customer")
public class CustomerApi {

    @Inject
    private CustomerService customerService;

    @Inject
    private UserService userService;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {

            Customer customer = customerService.findById(id);
            customerService.remove(id);
            userService.remove(Long.valueOf(customer.getUser().getUsername()));

            log.info("Customer removed successfully-ID : " + id);
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
    @Path("/findById/{id}")
    public Response findById(@PathParam(value = "id") Long id) {
        try {
            Customer customer = customerService.findById(id);
            CustomerVO customerVO = new CustomerVO(customer);

            if (customerVO != null) {
                log.info("Customer found successfully-ID : " + id);
                return Response.ok(customerVO).build();
            } else {
                log.error("Customer not found-ID : " + id);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for id: " + id)
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
    @Path("/findByFamily/{family}")
    public Response findByFamily(@PathParam(value = "family") String family) {
        try {
            List<Customer> customerList = customerService.findByFamily(family);
            List<CustomerVO> customerVOList = new ArrayList<>();
            for (Customer customer : customerList) {
                CustomerVO customerVO = new CustomerVO(customer);
                customerVOList.add(customerVO);
            }

            if (!customerVOList.isEmpty()) {
                log.info("Customer found successfully-family : " + family);
                return Response.ok(customerVOList).build();
            } else {
                log.error("Customer not found-family : " + family);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for family: " + family)
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
    @Path("/findByPhone/{phoneNumber}")
    public Response findByPhoneNumber(@PathParam(value = "phoneNumber") String phone) {
        try {
            Customer customer = customerService.findByPhoneNumber(phone);
            CustomerVO customerVO = new CustomerVO(customer);

            if (customerVO != null) {
                log.info("Customer found successfully-phone : " + phone);
                return Response.ok(customerVO).build();
            } else {
                log.error("Customer not found-phone : " + phone);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for phone: " + phone)
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