package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.model.entity.Role;
import com.beautysalon.beautysalonsystem.model.entity.User;
import com.beautysalon.beautysalonsystem.model.service.RoleService;
import com.beautysalon.beautysalonsystem.model.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "InitServlet", urlPatterns = "/init.do", loadOnStartup = 1)
public class InitServlet extends HttpServlet {
    @Inject
    private UserService userService;

    @Inject
    private RoleService roleService;

    @Override
    public void init() throws ServletException {
        try {
            Role admin = Role.builder().role("admin").build();
            roleService.save(admin);

            Role manager = Role.builder().role("manager").build();
            roleService.save(manager);

            Role customer = Role.builder().role("customer").build();
            roleService.save(customer);

            User user = User
                    .builder()
                    .username("admin")
                    .password("admin")
                    .role(admin)
                    .locked(false)
                    .deleted(false)
                    .build();
            userService.save(user);
            log.info("Admin Created !!!");
        } catch (Exception e) {
            log.error("Error in init !!!");
        }
    }
}
