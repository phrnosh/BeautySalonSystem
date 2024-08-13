package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.CardPayment;
import com.beautysalon.beautysalonsystem.model.service.CardPaymentService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/CardPayment.do")

public class CardPaymentServlet extends HttpServlet {

        @Inject
        private CardPaymentService cardPaymentService;

}
