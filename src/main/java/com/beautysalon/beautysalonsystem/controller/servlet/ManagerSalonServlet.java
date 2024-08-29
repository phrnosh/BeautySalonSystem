//package com.beautysalon.beautysalonsystem.controller.servlet;
//
//import com.beautysalon.beautysalonsystem.model.entity.Salon;
//import com.beautysalon.beautysalonsystem.model.service.ManagerService;
//import com.beautysalon.beautysalonsystem.model.service.SalonService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.inject.Inject;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@Slf4j
//@WebServlet("/manager/salon.do")
//public class ManagerSalonServlet extends HttpServlet {
//
//    @Inject
//    private ManagerService managerService;
//
//    @Inject
//    private SalonService salonService;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//
//            if (req.getParameter("cancel") != null){
//                Salon editSalon = salonService.findById(Long.parseLong(req.getParameter("cancel")));
//                salonService.setEditing(false);
//                salonService.edit(editSalon);
//                resp.sendRedirect("/managers/cinema.do");
//                return;
//            }
//
//            if (req.getParameter("edit") != null) {
//                Cinema editingCinema = cinemaService.findById(Long.parseLong(req.getParameter("edit")));
//                if (!editingCinema.isEditing()){
//                    editingCinema.setEditing(true);
//                    cinemaService.edit(editingCinema);
//                    req.getSession().setAttribute("editingCinema", editingCinema);
//                    req.getRequestDispatcher("/managers/manager-cinema-edit.jsp").forward(req, resp);
//                } else {
//                    resp.getWriter().write("<h1 style=\"background-color: green;\">" + "Record is editing by another user !!!" + "</h1>");
//                }
//            } else {
//                Manager manager = (Manager) req.getSession().getAttribute("manager");
//                req.getSession().setAttribute("cinema", managerService.findCinemaByManagerId(manager.getId()));
//                req.getRequestDispatcher("/managers/manager-cinema.jsp").forward(req, resp);
//            }
//        } catch (Exception e) {
//            resp.getWriter().write("<h1 style=\"background-color: green;\">" + e.getMessage() + "</h1>");
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        Cinema cinemaVo;
//
//        try {
//            cinemaVo = objectMapper.readValue(req.getInputStream(), Cinema.class);
//            Cinema editingCinema = (Cinema) req.getSession().getAttribute("editingCinema");
//            editingCinema.setName(cinemaVo.getName());
//            editingCinema.setStatus(cinemaVo.isStatus());
//            editingCinema.setDescription(cinemaVo.getDescription());
//            editingCinema.setAddress(cinemaVo.getAddress());
//            editingCinema.setEditing(false);
//            cinemaService.edit(editingCinema);
//
//
//            // Send success response with updated manager
//            resp.setStatus(HttpServletResponse.SC_OK);
//            PrintWriter out = resp.getWriter();
//            objectMapper.writeValue(out, cinemaVo); // Write manager object as JSON response
//            out.flush();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            // Send error response if something goes wrong
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            PrintWriter out = resp.getWriter();
//            out.write("{\"message\": \"Failed to update cinema.\"}");
//            out.flush();
//        }
//
//    }
//}
