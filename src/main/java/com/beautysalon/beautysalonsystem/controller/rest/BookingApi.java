package com.beautysalon.beautysalonsystem.controller.rest;


import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.model.entity.Booking;
import com.beautysalon.beautysalonsystem.model.entity.BookingVO;
import com.beautysalon.beautysalonsystem.model.entity.Salon;
import com.beautysalon.beautysalonsystem.model.entity.Timing;
import com.beautysalon.beautysalonsystem.model.service.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Path("/booking")
public class BookingApi {

    @Inject
    private BookingService bookingService;

    @Inject
    private ServicesService servicesService;

    @Inject
    private CustomerService customerService;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {

            Booking booking = bookingService.findById(id);
            booking.getCustomer().getBookingList().removeIf(booking1 -> booking1.getId().equals(id));
            customerService.edit(booking.getCustomer());

            bookingService.remove(id);

            log.info("Booking removed successfully-ID : " + id);
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

            Booking booking = bookingService.findById(id);

            if (booking != null) {
                BookingVO bookingVO = new BookingVO(booking);
//                bookingVO.setServicesName(servicesService.findById(booking.getTiming().getServices().getName());
                log.info("Booking found successfully-ID : " + id);
                return Response.ok(bookingVO).build();
            } else {
                log.info("Booking not found-id : " + id);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for ID: " + id)
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
    @Path("/findByTimingId/{id}")
    public Response findByShowTimeId(@PathParam(value = "id") Long id) {
        try {

            List<Booking> bookingList = bookingService.findByTimingId(id);
            List<BookingVO> bookingVOList = new ArrayList<>();
            for (Booking booking : bookingList) {
                BookingVO bookingVO = new BookingVO(booking);
//                bookingVO.setServicesName(servicesService.findById(booking.getSeatId()).getLabel());
                bookingVOList.add(bookingVO);
            }

            if (!bookingVOList.isEmpty()) {
                log.info("Booking found successfully-timing ID : " + id);
                return Response.ok(bookingVOList).build();
            } else {
                log.error("Booking not found-timing ID : " + id);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for timing ID: " + id)
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
    public Response findByCustomerPhoneNumber(@PathParam(value = "phoneNumber") String phoneNumber) {
        try {

            List<Booking> bookingList = bookingService.findByCustomerPhoneNumber(phoneNumber);
            List<BookingVO> bookingVOList = new ArrayList<>();
            for (Booking booking : bookingList) {
                BookingVO bookingVO = new BookingVO(booking);
//                bookingVO.setSeatLabel(seatService.findById(ticket.getSeatId()).getLabel());
                bookingVOList.add(bookingVO);
            }

            if (!bookingVOList.isEmpty()) {
                log.info("Booking found successfully-customer phone number : " + phoneNumber);
                return Response.ok(bookingVOList).build();
            } else {
                log.error("Booking not found-customer phone number : " + phoneNumber);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No records found for Phone Number: " + phoneNumber)
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
