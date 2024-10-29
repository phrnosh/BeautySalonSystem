package com.beautysalon.beautysalonsystem.controller.servlet;


import com.beautysalon.beautysalonsystem.model.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(urlPatterns = "/logout.do")
public class Logout extends HomeServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            log.info(user.getUsername() + " logged out successfully");
        } else {
            log.error("No user was found in the session during logout.");
        }
        req.getSession().invalidate();
        resp.sendRedirect("/postLogin.do");
    }

}