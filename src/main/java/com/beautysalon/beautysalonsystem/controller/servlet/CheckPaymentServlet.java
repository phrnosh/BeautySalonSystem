package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.model.service.CheckPaymentService;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet("/CheckPayment.do")
public class CheckPaymentServlet  extends HttpServlet {
    @Inject
    private CheckPaymentService checkPaymentService;


}
