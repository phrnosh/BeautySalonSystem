package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Bank;
import com.beautysalon.beautysalonsystem.model.service.BankService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.inject.Inject;
import java.io.IOException;

@WebServlet("/bank.do")
public class BankServlet extends HttpServlet {
    @Inject
    private BankService bankService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Bank bank =
                    Bank
                            .builder()
                            .name(req.getParameter("bank name"))
                            .accountNumber(req.getParameter("account number"))
                            .branchName(req.getParameter("branch name"))
                            .branchCode(Long.valueOf(req.getParameter("account code")))
                            .accountBalance(Long.valueOf(req.getParameter("account code")))
                             .build();

            BeanValidator<Bank> bankBeanValidator = new BeanValidator<>();

            if(bankBeanValidator.validate(bank).isEmpty()) {
                bankService.save(bank);
            }else{
                throw new Exception("Invalid Person Data !!!");  //error
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException

    {
        try {
            System.out.println("Bank PUT : ");
//           Gson gson = new Gson();
//        gson.fromJson(req.getReader(), CardPayment.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Bank bank = objectMapper.readValue(req.getReader(), Bank.class);
            bank.setEditing(false);
            bankService.edit(bank);
            resp.getWriter().write(" ");
        }catch (Exception e) {
        }
    }
}

