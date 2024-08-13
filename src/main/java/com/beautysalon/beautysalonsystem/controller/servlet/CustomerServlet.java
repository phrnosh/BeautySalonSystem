package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Admin;
import com.beautysalon.beautysalonsystem.model.entity.Customer;
import com.beautysalon.beautysalonsystem.model.entity.enums.UserState;
import com.beautysalon.beautysalonsystem.model.service.CustomerService;
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
@WebServlet("/customer.do")
public class CustomerServlet extends HttpServlet {

    Map<String, String> csrfTokens = new HashMap<>();

    @Inject
    private CustomerService customerService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Customer customer =
                    Customer
                            .builder()
                            .name(StringEscapeUtils.escapeHtml4(req.getParameter("name")))
                            .family(req.getParameter("family"))
                            .phoneNumber(req.getParameter("phone number"))
                            .build();

            BeanValidator<Customer> CustomerValidator = new BeanValidator<>();
            if(CustomerValidator.validate(customer).isEmpty()) {
                customerService.save(customer);
                resp.sendRedirect("/customer.do");
                log.info("customer saved successfully : " + customer.toString());
            }else{
                throw new Exception("Invalid Customer Data !!!");  //error
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("CUSTOMER PUT : ");
//           Gson gson = new Gson();
//        gson.fromJson(req.getReader(), Customer.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Customer customer = objectMapper.readValue(req.getReader(), Customer.class);
            customer.setEditing(false);
            customerService.edit(customer);
            resp.getWriter().write("javab");
        }catch (Exception e) {
        }
    }
}