package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Customer;
import com.beautysalon.beautysalonsystem.model.entity.Manager;
import com.beautysalon.beautysalonsystem.model.entity.Salon;
import com.beautysalon.beautysalonsystem.model.service.CustomerService;
import com.beautysalon.beautysalonsystem.model.service.SalonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet("/salon.do")
public class SalonServlet extends HttpServlet {
    Map<String, String> csrfTokens = new HashMap<>();

    @Inject
    private SalonService salonService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("cancel") != null){
                Salon editingSalon = salonService.findById(Long.parseLong(req.getParameter("cancel")));
                editingSalon.setEditing(false);
                salonService.edit(editingSalon);
                resp.sendRedirect("salon.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Salon editSalon = salonService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editSalon.isEditing()){
                    editSalon.setEditing(true);
                    salonService.edit(editSalon);
                    req.getSession().setAttribute("editSalon", editSalon);
                    req.getRequestDispatcher("/salon-info.jsp").forward(req, resp);
                } else {
                    resp.getWriter().write("Record is editing by another salon!");

                }
            } else {
                req.getSession().setAttribute("salon", salonService.findById(1L));
                req.getRequestDispatcher("/salon-info.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            resp.getWriter().write( e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Salon salon =
                    Salon
                            .builder()
                            .name(StringEscapeUtils.escapeHtml4(req.getParameter("name")))
                            .address(StringEscapeUtils.escapeHtml4(req.getParameter("address")))
                            .description(req.getParameter("description"))
                            .build();

            BeanValidator<Salon> salonBeanValidator = new BeanValidator<>();
            if(salonBeanValidator.validate(salon).isEmpty()) {
                salonService.save(salon);
                resp.sendRedirect("/salon.do");
                log.info("salon saved successfully : " + salon.toString());
            }else{
                throw new Exception("Invalid Salon Data !!!");  //error
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("SALON PUT : ");
            ObjectMapper objectMapper = new ObjectMapper();
            Salon salon = objectMapper.readValue(req.getReader(), Salon.class);
            Salon editSalon = (Salon) req.getSession().getAttribute("editSalon");
            editSalon.setName(salon.getName());
            editSalon.setAddress(salon.getAddress());
            editSalon.setDescription(salon.getDescription());
            editSalon.setEditing(false);
            salonService.edit(editSalon);
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, salon);
            out.flush();
            resp.getWriter().write("successful");
        }catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\": \"Failed to update salon.\"}");
        }
    }

}
