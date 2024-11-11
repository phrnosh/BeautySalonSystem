package com.beautysalon.beautysalonsystem.controller.servlet;
import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.model.entity.Salon;
import com.beautysalon.beautysalonsystem.model.entity.Services;
import com.beautysalon.beautysalonsystem.model.service.SalonService;
import com.beautysalon.beautysalonsystem.model.service.ServicesService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = "/search.do")
public class SearchServlet extends HttpServlet {

    @Inject
    private ServicesService servicesService;

    @Inject
    private SalonService salonService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//
//            List<Services> allFoundServices = new ArrayList<>();
//            String servicesText = "";
//
//            if (req.getParameter("servicesText") != null) {
//                servicesText = req.getParameter("servicesText");
//                allFoundServices = servicesService.findByText(servicesText);
//            }
//            log.info("Search Services by Text: "+ servicesText);
//            req.getSession().setAttribute("allFoundServices", allFoundServices);
//            req.getRequestDispatcher("/search.jsp").forward(req, resp);
//        } catch (Exception e) {
//            String errorMessage = e.getMessage();
//            req.getSession().setAttribute("errorMessage", errorMessage);
//            log.error(ExceptionWrapper.getMessage(e).toString());
//            resp.sendRedirect("/search.do");
//        }

        try {

            List<Salon> allFoundSalons = new ArrayList<>();
            String salonText = "";

            if (req.getParameter("salonText") != null) {
                salonText = req.getParameter("salonText");
                allFoundSalons = salonService.findByText(salonText);
            }
            log.info("Search Salons by Text: "+ salonText);
            req.getSession().setAttribute("allFoundSalons", allFoundSalons);
            req.getRequestDispatcher("/search.jsp").forward(req, resp);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/search.do");
        }
    }
}