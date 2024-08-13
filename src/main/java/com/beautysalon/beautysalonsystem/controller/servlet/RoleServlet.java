package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Customer;
import com.beautysalon.beautysalonsystem.model.entity.Role;
import com.beautysalon.beautysalonsystem.model.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet("/role.do")
public class RoleServlet extends HttpServlet {

    Map<String, String> csrfTokens = new HashMap<>();

    @Inject
    private RoleService roleService;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Role role =
                    Role
                            .builder()
                            .role(req.getParameter("role"))
                            .build();

            BeanValidator<Role> RoleValidator = new BeanValidator<>();
            if(RoleValidator.validate(role).isEmpty()) {
                roleService.save(role);
                resp.sendRedirect("/role.do");
                log.info("role saved successfully : " + role.toString());
            }else {
                throw new Exception("Invalid Role Data !!!");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("ROLE PUT : ");
//           Gson gson = new Gson();
//        gson.fromJson(req.getReader(), Role.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Role role = objectMapper.readValue(req.getReader(), Role.class);
            role.setEditing(false);
            roleService.edit(role);
            resp.getWriter().write("javab");
        }catch (Exception e) {
        }
    }
}