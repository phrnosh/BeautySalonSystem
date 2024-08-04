package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Manager;
import com.beautysalon.beautysalonsystem.model.entity.enums.UserState;
import com.beautysalon.beautysalonsystem.model.service.ManagerService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/manager.do")
public class ManagerServlet extends HttpServlet {
    @Inject
    private ManagerService managerService;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Manager manager =
                    Manager
                            .builder()
                            .name("farnoosh")
                            .family("gh")
                            .email("fgh@gmail.com")
                            .phoneNumber("09121234567")
                            .nationalCode("1234567890")
                            .status(UserState.Active)
                            .build();

            BeanValidator<Manager> ManagerValidator = new BeanValidator<>();
            if(ManagerValidator.validate(manager).isEmpty()) {
                managerService.save(manager);
            }else {
                throw new Exception("Invalid Manager Data !!!");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}