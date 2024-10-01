package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.CheckPayment;
import com.beautysalon.beautysalonsystem.model.service.CheckPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/checkPayment.do")
public class CheckPaymentServlet extends HttpServlet {
    @Inject
    private CheckPaymentService checkPaymentService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            CheckPayment checkPayment =
                    CheckPayment
                            .builder()
                            .price(Double.parseDouble(req.getParameter("price")))
                            .paymentDateTime(LocalDateTime.parse(req.getParameter("date time")))
                            .description(req.getParameter("description"))
                           .check(Long.valueOf(req.getParameter("check number")))
                            .build();

            BeanValidator<CheckPayment> checkPaymentBeanValidator = new BeanValidator<>();

            if(checkPaymentBeanValidator.validate(checkPayment).isEmpty()) {
                checkPaymentService.save(checkPayment);
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
            System.out.println("Check_Payment PUT : ");
//           Gson gson = new Gson();
//        gson.fromJson(req.getReader(), CardPayment.class);
            ObjectMapper objectMapper = new ObjectMapper();
            CheckPayment checkPayment = objectMapper.readValue(req.getReader(), CheckPayment.class);
            checkPayment.setEditing(false);
            checkPaymentService.edit(checkPayment);
            resp.getWriter().write(" ");
        }catch (Exception e) {
        }
    }
}

