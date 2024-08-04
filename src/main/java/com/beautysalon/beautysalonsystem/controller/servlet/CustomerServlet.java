package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Customer;
import com.beautysalon.beautysalonsystem.model.entity.enums.UserState;
import com.beautysalon.beautysalonsystem.model.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/customer.do")
public class CustomerServlet extends HttpServlet {

    @Inject
    private CustomerService customerService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Customer customer =
                    Customer
                            .builder()
                            .name("name")
                            .family("family")
                            .email("email@gmail.com")
                            .phoneNumber("09121234567")
                            .nationalCode("1234567890")
                            .status(UserState.Active)
                            .build();

            BeanValidator<Customer> CustomerValidator = new BeanValidator<>();
            if(CustomerValidator.validate(customer).isEmpty()) {
                customerService.save(customer);
            }else{
                throw new Exception("Invalid Customer Data !!!");  //error
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}