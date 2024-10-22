package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.model.entity.*;
import com.beautysalon.beautysalonsystem.model.service.SalonService;
import com.beautysalon.beautysalonsystem.model.service.ServicesService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = "/timing.do")
public class TimingServlet extends HttpServlet {


    @Inject
    private SalonService salonService;

    @Inject
    private ServicesService servicesService;

    @Inject
    private TimingService timingService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("timing.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("timing.do\n\n\n\n");

            User user = (User) req.getSession().getAttribute("user");

            Salon salon = null;
            String redirectPath = "";

            if (user.getRole().getRole().equals("manager")) {

                ManagerVO managerVO = (ManagerVO) req.getSession().getAttribute("manager");
                salon = salonService.findByName(managerVO.getSalonName());
                redirectPath = "/managers/manager-timing.jsp";

            }else if (user.getRole().getRole().equals("admin") || user.getRole().getRole().equals("moderator")) {

                if (req.getParameter("salonId") != null) {
                    salon = salonService.findById(Long.parseLong(req.getParameter("salonId")));
                    SalonVO salonVO = new SalonVO(salon);
                    req.getSession().setAttribute("salon", salonVO);
                } else {
                    SalonVO salonVO = (SalonVO) req.getSession().getAttribute("salon");
                    salon = salonService.findById(salonVO.getId());
                }

                redirectPath = "/salon/salon-timing.jsp";
            }

            List<Timing> salonTiming = salon.getTimingList();
            List<Services> salonServices = salonService.findSalonActiveServices(salon.getId());

            if (req.getParameter("cancel") != null) {
                Timing editingTiming = timingService.findById(Long.parseLong(req.getParameter("cancel")));
                editingTiming.setEditing(false);
                timingService.edit(editingTiming);
                resp.sendRedirect("/timing.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Timing editingTiming = timingService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingTiming.isEditing()) {
                    editingTiming.setEditing(true);
                    timingService.edit(editingTiming);
                    req.getSession().setAttribute("editingTiming", editingTiming);
                    req.getRequestDispatcher("/timing/timing-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/timing.do");
                }
            } else {
                req.getSession().setAttribute("salonTiming", salonTiming);
                req.getSession().setAttribute("salonServices", salonServices);
                req.getSession().setAttribute("allUsableServices", salonServices);
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/timing.do");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            SalonVO salonVO = (SalonVO) req.getSession().getAttribute("salon");
            Salon salon = salonService.findById(salonVO.getId());

            Services services = salonService.findServicesBySalonId(salon.getId());

            LocalDate timingDate = LocalDate.parse(req.getParameter("date"));
            LocalTime startHour = LocalTime.parse(req.getParameter("startTime"));
            LocalTime endHour = LocalTime.parse(req.getParameter("endTime"));
            LocalDateTime startTime = timingDate.atTime(startHour);
            LocalDateTime endTime = timingDate.atTime(endHour);

//todo
            Timing timing =
                    Timing
                            .builder()
                            .services(services)
                            .startTime(startTime)
                            .endTime(endTime)
                            .status(Boolean.parseBoolean(req.getParameter("status")))
                            .description(req.getParameter("description"))
                            .salon(salon)
                            .deleted(false)
                            .build();

            List<Timing> interferenceTiming = timingService.findTimingByServicesIdAndTime(services.getId(), startTime, endTime);
            if (interferenceTiming == null || interferenceTiming.isEmpty()) {
                timingService.save(timing);
                salon.addTiming(timing);
                salonService.edit(salon);
                log.info("Timing saved successfully-ID : " + timing.getId());
                resp.sendRedirect("/timing.do");
            } else {
                String errorMessage = "The Selected Service Is Occupied In This Time !!!";
                req.getSession().setAttribute("errorMessage", errorMessage);
                log.error(errorMessage);
                resp.sendRedirect("/timing.do");
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/timing.do");
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        TimingVO timingVO;

        try {
            timingVO = objectMapper.readValue(req.getInputStream(), TimingVO.class);
            Timing editingTiming = (Timing) req.getSession().getAttribute("editingTiming");
            editingTiming.setServices(servicesService.findByName(timingVO.getServicesName()));
            editingTiming.setStatus(timingVO.isStatus());
            editingTiming.setDescription(timingVO.getDescription());
            editingTiming.setEditing(false);
            timingService.edit(editingTiming);
            log.info("Timing edited successfully-ID : " + editingTiming.getId());

            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, timingVO);
            out.flush();

        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update timing.\"}");
            out.flush();
        }

    }

}