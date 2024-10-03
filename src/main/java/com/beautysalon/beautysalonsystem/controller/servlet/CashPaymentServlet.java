package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.CashPayment;
import com.beautysalon.beautysalonsystem.model.service.CashPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/cashPayment.do")
        public class CashPaymentServlet extends HttpServlet {
        @Inject
        private CashPaymentService cashPaymentService;

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        try{
        CashPayment cashPayment =
        CashPayment
        .builder()
//        .price(Float.parseFloat(req.getParameter("price")))
//        .paymentDateTime(LocalDateTime.parse(req.getParameter("date time")))
//        .description(req.getParameter("description"))
        .build();

        BeanValidator<CashPayment> cashPaymentBeanValidator = new BeanValidator<>();

        if(cashPaymentBeanValidator.validate(cashPayment).isEmpty()) {
        cashPaymentService.save(cashPayment);
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
        System.out.println("CASH_PAYMENT PUT : ");
//           Gson gson = new Gson();
//        gson.fromJson(req.getReader(), CardPayment.class);
        ObjectMapper objectMapper = new ObjectMapper();
        CashPayment cashPayment = objectMapper.readValue(req.getReader(), CashPayment.class);
        cashPayment.setEditing(false);
        cashPaymentService.edit(cashPayment);
        resp.getWriter().write(" ");
        }catch (Exception e) {
        }
        }
        }

