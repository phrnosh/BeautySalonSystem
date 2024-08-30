package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Customer;
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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet("/salon.do")
public class SalonServlet extends HttpServlet {
    Map<String, String> csrfTokens = new HashMap<>();

    @Inject
    private SalonService salonService;

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
            String username = req.getUserPrincipal().getName();

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
            salon.setEditing(false);
            salonService.edit(salon);
            resp.getWriter().write("successful");
        }catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\": \"Failed to update salon.\"}");
        }
    }

}
