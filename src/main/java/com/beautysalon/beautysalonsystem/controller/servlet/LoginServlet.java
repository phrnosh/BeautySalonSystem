package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.model.entity.User;
import com.beautysalon.beautysalonsystem.model.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@WebServlet(urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("login.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("login.do\n\n\n\n");


            if (req.getRemoteUser() != null) {
                String username = req.getRemoteUser();
                User user = (User) userService.findByUsername(username);

                if (!user.isLocked()){

                    req.getSession().setAttribute("user", user);
                    log.info(username + " logged in");

                    switch (user.getRole().getRole()) {
                        case "admin":
                            resp.sendRedirect("/admin.do");
                            break;
                        case "manager":
                            resp.sendRedirect("/manager.do");
                            break;
                        case "customer":
                            resp.sendRedirect("/customer.do");
                            break;
                    }

                } else {
                    log.error(user.getUsername() + " is locked");
                    resp.sendRedirect("error-423.jsp");
                }
            } else {
                log.error(req.getRemoteUser() + " tried to login !!!");
                // Default to login page if no valid role
                resp.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());
            throw new RuntimeException(e);
        }
    }

}