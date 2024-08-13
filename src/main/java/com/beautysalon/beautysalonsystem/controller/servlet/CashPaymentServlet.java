package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.model.service.CardPaymentService;
import com.beautysalon.beautysalonsystem.model.service.CashPaymentService;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet("/CahPayment.do")

public class CashPaymentServlet  extends HttpServlet {

    @Inject
    private CashPaymentService cashPaymentService;

}
