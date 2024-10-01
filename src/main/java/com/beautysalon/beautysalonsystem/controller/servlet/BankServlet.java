package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Bank;
import com.beautysalon.beautysalonsystem.model.entity.User;
import com.beautysalon.beautysalonsystem.model.service.BankService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet("/bank.do")
public class BankServlet extends HttpServlet {
    @Inject
    private BankService bankService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            User user = (User) req.getSession().getAttribute("user");

            if (req.getParameter("cancel") != null) {
                Bank editingBank = bankService.findById(Long.parseLong(req.getParameter("cancel")));
                editingBank.setEditing(false);
                bankService.edit(editingBank);
                resp.sendRedirect("/bank.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Bank editingBank = bankService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingBank.isEditing()) {
                    editingBank.setEditing(true);
                    bankService.edit(editingBank);
                    req.getSession().setAttribute("editingBank", editingBank);
                    log.info("Bank Id : " + editingBank.getId() + " is editing by : " + user.getUsername());
                    req.getRequestDispatcher("/banks/bank-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/bank.do");
                }
            } else {
                req.getSession().setAttribute("bankList", bankService.findAll());
                req.getRequestDispatcher("/banks/bank.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/bank.do");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String name = req.getParameter("name").toUpperCase();
            String accountNumber = req.getParameter("accountNumber");
            String branchCode = req.getParameter("branchCode");
            String branchName = req.getParameter("branchName").toUpperCase();
            String accountBalance = req.getParameter("accountBalance");
            boolean status = Boolean.parseBoolean(req.getParameter("status"));


            Bank bank = Bank.builder()
                    .name(name)
                    .branchName(branchName)
                    .branchCode(Long.parseLong(branchCode))
                    .accountBalance(Double.parseDouble(accountBalance))
                    .accountNumber(accountNumber)
                    .status(status)
                    .build();

            BeanValidator<Bank> validator = new BeanValidator<>();
            if (validator.validate(bank).isEmpty()) {
                bankService.save(bank);
                log.info("Bank Saved With This Id : " + bank.getId());
                resp.sendRedirect("/bank.do");
            } else {
                String errorMessage = "Invalid Bank Data !!!";
                req.getSession().setAttribute("errorMessage", errorMessage);
                log.error(errorMessage);
                resp.sendRedirect("/bank.do");
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/bank.do");
        }
    }



    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        Bank bankAb;

        try {
            bankAb = objectMapper.readValue(req.getInputStream(), Bank.class);
            Bank editingBank = (Bank) req.getSession().getAttribute("editingBank");
            editingBank.setEditing(false);
            bankService.edit(editingBank);

            editingBank.setBranchName(bankAb.getBranchName());
            editingBank.setBranchCode(bankAb.getBranchCode());
            editingBank.setStatus(bankAb.isStatus());

            BeanValidator<Bank> bankValidator = new BeanValidator<>();
            if (bankValidator.validate(bankAb).isEmpty()) {
                bankService.edit(editingBank);
                log.info("Bank updated successfully : " + editingBank.getId());

                resp.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = resp.getWriter();
                objectMapper.writeValue(out, bankAb);
                out.flush();
            } else {
                log.error("Invalid Bank Data For Update !!!");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                PrintWriter out = resp.getWriter();
                out.write("{\"message\": \"Invalid Bank Data.\"}");
                out.flush();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update bank.\"}");
            out.flush();
        }
    }
}