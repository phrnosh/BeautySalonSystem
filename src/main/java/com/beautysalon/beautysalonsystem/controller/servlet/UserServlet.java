package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Customer;
import com.beautysalon.beautysalonsystem.model.entity.Role;
import com.beautysalon.beautysalonsystem.model.entity.User;
import com.beautysalon.beautysalonsystem.model.service.RoleService;
import com.beautysalon.beautysalonsystem.model.service.UserService;
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
@WebServlet("/user.do")
public class UserServlet extends HttpServlet {

    Map<String, String> csrfTokens = new HashMap<>();

    @Inject
    private UserService userService;
    @Inject
    private RoleService roleService;
    @Inject
    private User user;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("UserServlet - Get");
        try {
            req.getSession().setAttribute("userList", roleService.findAll());
            req.getRequestDispatcher("/jsp/form/save/user-form.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("User - Get : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("UserServlet - Post");
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            user = User.builder()
                    .username(username)
                    .password(password)
                    .deleted(false)
                    .build();

            BeanValidator<User> validator = new BeanValidator<>();
            String validationResult = validator.validate(user).toString();
            if (validationResult != null) {
                resp.setStatus(400);
                resp.getWriter().write(validationResult);
                return;
            }

            if (userService.findByUsername(username).isEmpty()) {
                userService.save(user);

                Role userRole = Role.builder()
                        .role("user")
                        .deleted(false)
                        .build();
//                if (roleService.findByUsernameAndRoleName(user.getUsername(), "user").isEmpty()) {
//                    rolesService.save(userRole);
//                    log.info("New user role saved");
//                }

                req.getSession().removeAttribute("duplicateUsername");
                resp.sendRedirect("/user.do");

            } else {
                String errorMessage = "نام کاربری تکراری است!";
                req.getSession().setAttribute("duplicateUsername", errorMessage);
                resp.sendRedirect("/user.do");
            }
        } catch (Exception e) {
            log.error("User - POST : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}