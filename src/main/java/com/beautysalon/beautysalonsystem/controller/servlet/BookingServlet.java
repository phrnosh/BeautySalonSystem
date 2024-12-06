package com.beautysalon.beautysalonsystem.controller.servlet;


import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;

import com.beautysalon.beautysalonsystem.model.entity.*;

import com.beautysalon.beautysalonsystem.model.service.BankService;
import com.beautysalon.beautysalonsystem.model.service.BookingService;
import com.beautysalon.beautysalonsystem.model.service.CustomerService;
import com.beautysalon.beautysalonsystem.model.service.TimingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Enumeration;
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = "/booking.do")
public class BookingServlet extends HttpServlet {
    @Inject
    private BookingService bookingService;
    
    @Inject
    private TimingService timingService;
    
    @Inject
    private BankService bankService;

    @Inject
    private CustomerService customerService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("booking.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("booking.do\n\n\n\n");

            User user = (User) req.getSession().getAttribute("user");
            String redirectPath = "";

            if (user != null && user.getRole().getRole().equals("customer")) {


                List<Booking> bookingList = bookingService.findByCustomerPhoneNumber(user.getUsername());
                List<BookingVO> bookingVOList = new ArrayList<>();
                for (Booking booking : bookingList) {
                    BookingVO bookingVO = new BookingVO(booking);
                    bookingVOList.add(bookingVO);
                }
                req.getSession().setAttribute("customerBookings", bookingVOList);
                redirectPath = "/customers/bookings.jsp";

            } else {

                redirectPath = "/admin/find-booking.jsp";

            }


            if (req.getParameter("cancel") != null) {
                Booking editingBooking = bookingService.findById(Long.parseLong(req.getParameter("cancel")));
                editingBooking.setEditing(false);
                bookingService.edit(editingBooking);
                resp.sendRedirect("/booking.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Booking editingBooking = bookingService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingBooking.isEditing()) {
                    editingBooking.setEditing(true);
                    bookingService.edit(editingBooking);
                    req.getSession().setAttribute("editingBooking", editingBooking);
                    req.getRequestDispatcher("/bookings/booking-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/booking.do");
                }
            } else {
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/booking.do");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
//            TimingVO timingVO = (TimingVO) req.getSession().getAttribute("selectedTiming");
//            Timing timing = timingService.findById(timingVO.getId());

            Timing timing = timingService.findById(Long.parseLong(req.getParameter("selectTimingId")));

            CustomerVO customerVO = (CustomerVO) req.getSession().getAttribute("customer");
            Customer customer = customerService.findById(customerVO.getId());

            List<Long> bookingIds = new ArrayList<>();


                    Booking booking =
                            Booking
                                    .builder()
                                    .customer(customer)
                                    .timing(timing)
                                    .issueTime(LocalDateTime.now())
                                    .reserved(true)
                                    .build();

                    bookingService.save(booking);
                    bookingIds.add(booking.getId());
                    timing.setStatus(false);
                    timingService.edit(timing);
                    log.info("Booking saved successfully ID : " + booking.getId());

                req.getSession().setAttribute("bookingIds", bookingIds);

            resp.sendRedirect("/booking.do");


        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/booking.do");
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        Booking bookingAb;
        try {

            bookingAb = objectMapper.readValue(req.getInputStream(), Booking.class);
            Booking editingBooking = (Booking) req.getSession().getAttribute("editingBooking");
            editingBooking.setReserved(bookingAb.isReserved());
            editingBooking.setEditing(false);
            bookingService.edit(editingBooking);
            log.info("Booking updated successfully : " + editingBooking.getId());

            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, bookingAb);
            out.flush();

        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update booking.\"}");
            out.flush();
        }
    }


}
