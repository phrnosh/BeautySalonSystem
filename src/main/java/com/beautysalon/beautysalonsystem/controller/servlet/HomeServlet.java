package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.model.entity.*;
import com.beautysalon.beautysalonsystem.model.service.SalonService;
import com.beautysalon.beautysalonsystem.model.service.ServicesService;
import com.beautysalon.beautysalonsystem.model.service.TimingService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = "/salonHome.do")
public class HomeServlet extends HttpServlet {

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
            System.out.println("salonHome.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("salonHome.do\n\n\n\n");


            if (req.getParameter("selectDate") != null) {

                 LocalDate selectedDate = LocalDate.parse(req.getParameter("selectDate"));

                 req.getSession().setAttribute("selectedDate", selectedDate);
//                 List<Salon> salonList = timingService.findDistinctSalonByServicesIdAndDate(Long.parseLong(req.getSession().getAttribute("servicesId").toString()), selectedDate);
//                 List<SalonVO> salonVOList = new ArrayList<>();
//                 for (Salon salon : salonList) {
//                     SalonVO salonVO = new SalonVO(salon);
//                     salonVOList.add(salonVO);
//                 }
                Long servicesId = Long.parseLong(req.getSession().getAttribute("servicesId").toString());
                Long salonId = Long.parseLong(req.getSession().getAttribute("salonId").toString());
                List<Timing> timings = timingService.findBySalonIdAndServicesIdAndDate(salonId, servicesId, selectedDate);
            List<TimingVO> timingVOList = new ArrayList<>();
            for (Timing timing : timings) {
                TimingVO timingVO = new TimingVO(timing);
                timingVOList.add(timingVO);
            }

                System.out.println(timingVOList);
          req.getSession().setAttribute("timing", timingVOList);


                 req.getRequestDispatcher("/timing-select.jsp").forward(req, resp);



             } else if (req.getParameter("selectServices") != null) {
                Long servicesId = Long.parseLong(req.getParameter("selectServices"));
                req.getSession().setAttribute("servicesId", servicesId);
                req.getSession().setAttribute("selectedServices", servicesService.findById(servicesId));
                List<LocalDate> dateList = timingService.findDistinctDatesByServicesId(servicesId);

                System.out.println(dateList);
                req.getSession().setAttribute("servicesDate", dateList);
                req.getRequestDispatcher("/services-date-select.jsp").forward(req, resp);


             } else if (req.getParameter("selectSalon") != null) {

            Long salonId = Long.parseLong(req.getParameter("selectSalon"));
            Salon salon = salonService.findById(salonId);
            SalonVO salonVO = new SalonVO(salon);
            req.getSession().setAttribute("selectedSalon", salonVO);
            req.getSession().setAttribute("salonId", salonId);
//            Long servicesId = Long.parseLong(req.getSession().getAttribute("servicesId").toString());
//            LocalDate selectedDate = LocalDate.parse(req.getSession().getAttribute("selectedDate").toString());
//            List<Timing> timings = timingService.findBySalonIdAndServicesIdAndDate(salonId, servicesId, selectedDate);
//            List<TimingVO> timingVOList = new ArrayList<>();
//            for (Timing timing : timings) {
//                TimingVO timingVO = new TimingVO(timing);
//                timingVOList.add(timingVO);
//          req.getSession().setAttribute("timing", timingVOList);

             req.getSession().setAttribute("services", salon.getServicesList());
            req.getRequestDispatcher("/services-select.jsp").forward(req, resp);


             } else {

            List<Salon> allActiveSalon = new ArrayList<>();

//                if(req.getParameter("findHairstyle") != null) {
//                    allActiveServices = servicesService.findAvailableServicesByType("HAIRSTYLE");
//                } else if(req.getParameter("findMakeup") != null) {
//                    allActiveServices = servicesService.findAvailableServicesByType("MAKEUP");
//                } else if (req.getParameter("findSanitary") != null) {
//                    allActiveServices = servicesService.findAvailableServicesByType("SANITARY");
//                } else if (req.getParameter("findNails") != null) {
//                    allActiveServices = servicesService.findAvailableServicesByType("NAILS");
//                } else {
//                    allActiveServices = servicesService.findAvailableServices();
//                }
//
//                for (Services services : allActiveServices){
//                    if (services.getAttachments() != null && !services.getAttachments().isEmpty()){
//                        Collections.shuffle(services.getAttachments());
//                    }
//                }

                allActiveSalon = salonService.findAllLimit();

                for (Salon salon : allActiveSalon){
                    if (salon.getAttachments() != null && !salon.getAttachments().isEmpty()){
                        Collections.shuffle(salon.getAttachments());
                    }
                }

                req.getSession().setAttribute("allActiveSalon", allActiveSalon);
                req.getRequestDispatcher("/customers/panel.jsp").forward(req, resp);

            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
//            resp.sendRedirect("/salonHome.do");
        }
    }
}