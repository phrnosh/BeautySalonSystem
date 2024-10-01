package com.beautysalon.beautysalonsystem.controller.servlet;


import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Booking;
import com.beautysalon.beautysalonsystem.model.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/booking.do")
public class BookingServlet extends HttpServlet {
    @Inject
    private BookingService bookingService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Booking booking =
                    Booking
                            .builder()
                //            .customer(req.getParameter("customer"))
                            //TODO:You don't have localDateTime Field in your Booking Class .
//                            .localDateTime(LocalDateTime.parse(req.getParameter("local date")))
                            .build();

            BeanValidator<Booking> bookingBeanValidator = new BeanValidator<>();

            if(bookingBeanValidator.validate(booking).isEmpty()) {
                bookingService.save(booking);
            }else{
                throw new Exception("Invalid Person Data !!!");  //error
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException

    {
        try {
            System.out.println("Booking PUT : ");
//           Gson gson = new Gson();
//        gson.fromJson(req.getReader(), CardPayment.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Booking booking = objectMapper.readValue(req.getReader(), Booking.class);
            booking.setEditing(false);
            bookingService.edit(booking);
            resp.getWriter().write(" ");
        }catch (Exception e) {
        }
    }
}

