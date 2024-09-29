package com.beautysalon.beautysalonsystem.controller.rest;

import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.model.entity.Salon;
import com.beautysalon.beautysalonsystem.model.entity.Timing;
import com.beautysalon.beautysalonsystem.model.service.BookingService;
import com.beautysalon.beautysalonsystem.model.service.SalonService;
import com.beautysalon.beautysalonsystem.model.service.TimingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/timing")
public class TimingApi {

    @Inject
    private BookingService bookingService;

    @Inject
    private TimingService timingService;

    @Inject
    private SalonService salonService;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {

            if (!bookingService.findReservedTimingByServicesId(id).isEmpty()) {
                log.error("Some bookings of this timing has been sold, can not remove timing-id : " + id);
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("Some bookings of this timing has been sold !!!")
                        .build();
            } else {
                Timing timingToRemove = timingService.findById(id);
                Salon salon = timingToRemove.getSalon();
                timingService.remove(id);
                salon.getTimingList().removeIf(timing -> timing.getId().equals(timingToRemove.getId()));
                salonService.edit(salon);

                log.info("Timing removed successfully-ID : " + id);
                return Response.accepted().build();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
    }
}