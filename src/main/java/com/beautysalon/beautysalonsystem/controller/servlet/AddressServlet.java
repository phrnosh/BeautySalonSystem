package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Address;
import com.beautysalon.beautysalonsystem.model.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/address.do")
public class AddressServlet extends HttpServlet {
    @Inject
    private AddressService addressService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Address address =
                    Address
                            .builder()
                            .countryName(req.getParameter("country name"))
                            .stateName(req.getParameter("state name"))
                            .cityName(req.getParameter("bank name"))
                            .build();

            BeanValidator<Address> addressBeanValidator= new BeanValidator<>();

            if(addressBeanValidator.validate(address).isEmpty()) {
                addressService.save(address);
            }else{
                throw new Exception("Invalid Person Data !!!");  //error
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("Address PUT : ");

            ObjectMapper objectMapper = new ObjectMapper();
            Address address = objectMapper.readValue(req.getReader(), Address.class);
            address.setEditing(false);
            addressService.edit(address);
            resp.getWriter().write(" ");
        }catch (Exception e) {
        }
    }
}


