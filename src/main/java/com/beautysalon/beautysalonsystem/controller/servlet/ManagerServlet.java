package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Customer;
import com.beautysalon.beautysalonsystem.model.entity.Manager;
import com.beautysalon.beautysalonsystem.model.entity.enums.UserState;
import com.beautysalon.beautysalonsystem.model.service.ManagerService;
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
@WebServlet("/manager.do")
public class ManagerServlet extends HttpServlet {

    Map<String, String> csrfTokens = new HashMap<>();

    @Inject
    private ManagerService managerService;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Manager manager =
                    Manager
                            .builder()
                            .name(StringEscapeUtils.escapeHtml4(req.getParameter("name")))
                            .family(req.getParameter("family"))
                            .phoneNumber(req.getParameter("phone number"))
                            .build();

            BeanValidator<Manager> ManagerValidator = new BeanValidator<>();
            if(ManagerValidator.validate(manager).isEmpty()) {
                managerService.save(manager);
                resp.sendRedirect("/manager.do");
                log.info("manager saved successfully : " + manager.toString());
            }else {
                throw new Exception("Invalid Manager Data !!!");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("MANAGER PUT : ");
//           Gson gson = new Gson();
//        gson.fromJson(req.getReader(), Customer.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Manager manager = objectMapper.readValue(req.getReader(), Manager.class);
            manager.setEditing(false);
            managerService.edit(manager);
            resp.getWriter().write("javab");
        }catch (Exception e) {
        }
    }
}