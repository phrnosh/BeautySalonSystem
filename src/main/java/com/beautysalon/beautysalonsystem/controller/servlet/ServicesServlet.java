package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Services;
import com.beautysalon.beautysalonsystem.model.service.ServicesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/Services.do")
public class ServicesServlet extends HttpServlet {
    @Inject
    private ServicesService servicesService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Services services =
                    Services
                            .builder()
                            .name(req.getParameter("name"))
                            .description(req.getParameter("description"))
                            .stylistName(req.getParameter("stylistName"))
                            .status(Boolean.parseBoolean(req.getParameter("status")))
                            .dateOfModified(LocalDateTime.parse(req.getParameter("date of modified")))
                            .build();

            BeanValidator<Services> servicesBeanValidator = new BeanValidator<>();

            if(servicesBeanValidator.validate(services).isEmpty()) {
                servicesService.save(services);
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
            System.out.println("Services PUT : ");
//           Gson gson = new Gson();
//        gson.fromJson(req.getReader(), CardPayment.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Services services = objectMapper.readValue(req.getReader(), Services.class);
            services.setEditing(false);
            servicesService.edit(services);
            resp.getWriter().write(" ");
        }catch (Exception e) {
        }
    }
}