package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.CardPayment;
import com.beautysalon.beautysalonsystem.model.service.CardPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;


@WebServlet("/cardPayment.do")
public class  CardPaymentServlet extends HttpServlet {
    @Inject
    private CardPaymentService cardPaymentService;

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try{
                CardPayment cardPayment =
                        CardPayment
                                .builder()
                                .price(Float.parseFloat(req.getParameter("price")))
                                .paymentDateTime(LocalDateTime.parse(req.getParameter("date time")))
                                .description(req.getParameter("description"))
                                .bankName(req.getParameter("bank name"))
                                .cardNumber(Integer.parseInt(req.getParameter("bank name")))
                                .build();

                BeanValidator<CardPayment> cardPaymentBeanValidator = new BeanValidator<>();

                if(cardPaymentBeanValidator.validate(cardPayment).isEmpty()) {
                    cardPaymentService.save(cardPayment);
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
            System.out.println("CARD_PAYMENT PUT : ");
//           Gson gson = new Gson();
//        gson.fromJson(req.getReader(), CardPayment.class);
            ObjectMapper objectMapper = new ObjectMapper();
            CardPayment cardPayment = objectMapper.readValue(req.getReader(), CardPayment.class);
            cardPayment.setEditing(false);
            cardPaymentService.edit(cardPayment);
            resp.getWriter().write(" ");
        }catch (Exception e) {
        }
    }
}

