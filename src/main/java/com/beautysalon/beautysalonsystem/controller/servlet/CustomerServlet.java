package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.*;
import com.beautysalon.beautysalonsystem.model.entity.enums.UserState;
import com.beautysalon.beautysalonsystem.model.service.AttachmentService;
import com.beautysalon.beautysalonsystem.model.service.CustomerService;
import com.beautysalon.beautysalonsystem.model.service.RoleService;
import com.beautysalon.beautysalonsystem.model.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Slf4j
@WebServlet("/customer.do")
public class CustomerServlet extends HttpServlet {

    Map<String, String> csrfTokens = new HashMap<>();

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
            System.out.println("customer.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("customer.do\n\n\n\n");


            User user = (User) req.getSession().getAttribute("user");
            String redirectPath = "";

            if (user == null) {
                resp.sendRedirect("/sign-up.jsp");
                return;
            }

            if (user.getRole().getRole().equals("customer")) {

                Customer customer = (Customer) customerService.findByUsername(user.getUsername());
                CustomerVO customerVO = new CustomerVO(customer);
                req.getSession().setAttribute("customer", customerVO);
                redirectPath = "/customers/customer-panel.jsp";

            } else if (user.getRole().getRole().equals("admin")) {
                List<Customer> customerList = customerService.findAll();
                List<CustomerVO> customerVOList = new ArrayList<>();
                for (Customer customer : customerList) {
                    CustomerVO customerVO = new CustomerVO(customer);
                    customerVOList.add(customerVO);
                }

                req.getSession().setAttribute("allCustomers", customerVOList);
                redirectPath = "/admin/customers.jsp";
            }


            if (req.getParameter("cancel") != null) {
                Customer editingCustomer = customerService.findById(Long.parseLong(req.getParameter("cancel")));
                editingCustomer.setEditing(false);
                customerService.edit(editingCustomer);
                resp.sendRedirect("/customer.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Customer editingCustomer = customerService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingCustomer.isEditing()) {
                    editingCustomer.setEditing(true);
                    customerService.edit(editingCustomer);
                    req.getSession().setAttribute("editingCustomer", editingCustomer);
                    req.getRequestDispatcher("/customers/customer-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/customer.do");
                }
            } else {
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/customer.do");
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
                Role role = (Role) roleService.FindByRole("customer");

                User user =
                        User
                                .builder()
                                .username(req.getParameter("phoneNumber"))
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


                if (userService.findByUsername(req.getParameter("phoneNumber")) != null) {
                    String errorMessage = "Duplicate username(phoneNumber) !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("sign-up.jsp");
                    return;
                }

                Customer customer =
                        Customer
                                .builder()
                                .name(req.getParameter("name").toUpperCase())
                                .family(req.getParameter("family").toUpperCase())
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
                    String errorMessage = "Invalid Customer Data !!!";
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

        Customer customerAb;
        try {

            customerAb = objectMapper.readValue(req.getInputStream(), Customer.class);
            Customer editingCustomer = (Customer) req.getSession().getAttribute("editingCustomer");
            editingCustomer.setEditing(false);
            customerService.edit(editingCustomer);

            editingCustomer.setName(customerAb.getName().toUpperCase());
            editingCustomer.setFamily(customerAb.getFamily().toUpperCase());
            editingCustomer.setPhoneNumber(customerAb.getPhoneNumber());
            editingCustomer.setEmail(customerAb.getEmail());

            User user = (User) userService.findByUsername(editingCustomer.getPhoneNumber());
            user.setUsername(customerAb.getPhoneNumber());
            userService.edit(user);

            BeanValidator<Customer> customerValidator = new BeanValidator<>();
            if (customerValidator.validate(editingCustomer).isEmpty()) {
                customerService.edit(editingCustomer);
                log.info("Customer updated successfully : " + editingCustomer.getId());

                resp.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = resp.getWriter();
                objectMapper.writeValue(out, customerAb);
                out.flush();
            } else {
                log.error("Invalid Customer Data For Update !!!");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                PrintWriter out = resp.getWriter();
                out.write("{\"message\": \"Invalid Customer Data.\"}");
                out.flush();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update customer.\"}");
            out.flush();
        }
    }

}