package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Customer;
import com.beautysalon.beautysalonsystem.model.entity.Role;
import com.beautysalon.beautysalonsystem.model.entity.User;
import com.beautysalon.beautysalonsystem.model.service.CustomerService;
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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;


@Slf4j
@WebServlet(urlPatterns = "/user.do")
public class UserServlet extends HttpServlet {

    @Inject
    private CustomerService customerService;

    @Inject
    private RoleService roleService;

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("user.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("user.do\n\n\n\n");


            User user = (User) req.getSession().getAttribute("user");
            String redirectPath = "";

            if (user == null) {
                resp.sendRedirect("/reset-password.jsp");
                return;
            }

            if (user.getRole().getRole().equals("customer")) {

                redirectPath = "/customers/customer-user.jsp";

            } else if (user.getRole().getRole().equals("manager")) {

                redirectPath = "/managers/manager-user.jsp";

            } else if (user.getRole().getRole().equals("moderator") || user.getRole().getRole().equals("admin")) {

                req.getSession().setAttribute("allUsers", userService.findAll());
                redirectPath = "/admin/users.jsp";

            }


            if (req.getParameter("cancel") != null) {
                User editingUser = (User) userService.findByUsername(req.getParameter("cancel"));
                editingUser.setEditing(false);
                userService.edit(editingUser);
                resp.sendRedirect("/postLogin.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                User editingUser = (User) userService.findByUsername(req.getParameter("edit"));
                if (!editingUser.isEditing()) {
                    editingUser.setEditing(true);
                    userService.edit(editingUser);
                    req.getSession().setAttribute("editingUser", editingUser);
                    req.getRequestDispatcher("/users/user-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/user.do");
                }
            } else {
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/user.do");
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Role role = (Role) roleService.FindByRole("customer");

            User user =
                    User
                            .builder()
                            .username(req.getParameter("username"))
                            .password(req.getParameter("password"))
                            .role(role)
                            .locked(false)
                            .deleted(false)
                            .build();

            BeanValidator<User> userValidator = new BeanValidator<>();
            if (!userValidator.validate(user).isEmpty()) {
                String errorMessage = "Invalid User Data!";
                req.getSession().setAttribute("errorMessage", errorMessage);
                log.error(errorMessage);
                resp.sendRedirect("/sign-up.jsp");
                return;
            }

            if (userService.findByUsername(req.getParameter("username")) != null){
                String errorMessage = "Duplicate username(phoneNumber) !!!";
                req.getSession().setAttribute("errorMessage", errorMessage);
                log.error(errorMessage);
                resp.sendRedirect("/sign-up.jsp");
                return;
            }


            Customer customer =
                    Customer
                            .builder()
                            .name(req.getParameter("name"))
                            .family(req.getParameter("family"))
                            .phoneNumber(req.getParameter("phoneNumber"))
                            .email(req.getParameter("email"))
                            .user(user)
                            .deleted(false)
                            .build();


            BeanValidator<Customer> customerValidator = new BeanValidator<>();
            if (customerValidator.validate(customer).isEmpty()) {
                customerService.save(customer);
                req.getSession().setAttribute("user", user);
                log.info("Customer saved successfully : " + customer.getPhoneNumber());
                resp.sendRedirect("/customer.do");
            } else {
                String errorMessage = "Invalid User Data !!!";
                req.getSession().setAttribute("errorMessage", errorMessage);
                log.error(errorMessage);
                resp.sendRedirect("/sign-up.jsp");
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/sign-up.jsp");
        }

    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        User userAb;
        try {


            userAb = objectMapper.readValue(req.getInputStream(), User.class);
            User editingUser = (User) req.getSession().getAttribute("editingUser");
            editingUser.setEditing(false);
            userService.edit(editingUser);

            editingUser.setPassword(userAb.getPassword());
            editingUser.setLocked(userAb.isLocked());

            BeanValidator<User> userValidator = new BeanValidator<>();
            if (userValidator.validate(editingUser).isEmpty()) {
                userService.edit(editingUser);
                log.info("User updated successfully : " + editingUser.getUsername());

                resp.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = resp.getWriter();
                objectMapper.writeValue(out, userAb);
                out.flush();
            } else {
                log.error("Invalid User Data For Update !!!");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                PrintWriter out = resp.getWriter();
                out.write("{\"message\": \"Invalid User Data.\"}");
                out.flush();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update user.\"}");
            out.flush();
        }
    }
}