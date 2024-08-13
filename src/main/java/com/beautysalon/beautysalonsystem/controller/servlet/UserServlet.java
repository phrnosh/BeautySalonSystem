package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Customer;
import com.beautysalon.beautysalonsystem.model.entity.User;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{
            User user =
                    User
                            .builder()
                            .username(StringEscapeUtils.escapeHtml4(req.getParameter("aaa")))
                            .password(req.getParameter("123"))
                            .build();

            BeanValidator<User> personValidator = new BeanValidator<>();

            if(personValidator.validate(user).isEmpty()) {
                userService.save(user);
                resp.sendRedirect("/user.do");
                log.info("user saved successfully : " + user.toString());
            }else{
                throw new Exception("Invalid User Data !!!");  //error
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("USER PUT : ");
//           Gson gson = new Gson();
//        gson.fromJson(req.getReader(), Customer.class);
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(req.getReader(), User.class);
            user.setEditing(false);
            userService.edit(user);
            resp.getWriter().write("javab");
        }catch (Exception e) {
        }
    }
}
