package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Customer;
import com.beautysalon.beautysalonsystem.model.entity.Manager;
import com.beautysalon.beautysalonsystem.model.entity.enums.UserState;
import com.beautysalon.beautysalonsystem.model.service.ManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet("/manager.do")
public class ManagerServlet extends HttpServlet {

    Map<String, String> csrfTokens = new HashMap<>();

    @Inject
    private ManagerService managerService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("cancel") != null){
                Manager editingManager = managerService.findById(Long.parseLong(req.getParameter("cancel")));
                editingManager.setEditing(false);
                managerService.edit(editingManager);
                resp.sendRedirect("manager.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Manager editManager = managerService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editManager.isEditing()){
                    editManager.setEditing(true);
                    managerService.edit(editManager);
                    req.getSession().setAttribute("editManager", editManager);
                    req.getRequestDispatcher("/managers/manager-edit.jsp").forward(req, resp);
                } else {
                    resp.getWriter().write("Record is editing by another user!");

                }
            } else {
                req.getSession().setAttribute("manager", managerService.findById(1L));
                req.getRequestDispatcher("/managers/manager-register.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            resp.getWriter().write( e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Manager manager =
                    Manager
                            .builder()
                            .name(StringEscapeUtils.escapeHtml4(req.getParameter("name")))
                            .family(req.getParameter("family"))
                            .phoneNumber(req.getParameter("phone number"))
                            .build();

            BeanValidator<Manager> ManagerValidator = new BeanValidator<>();
            if(ManagerValidator.validate(manager).isEmpty()) {
                managerService.save(manager);
                resp.sendRedirect("/manager.do");
                log.info("manager saved successfully : " + manager.toString());
            }else {
                throw new Exception("Invalid Manager Data !!!");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("MANAGER PUT : ");
//           Gson gson = new Gson();
//        gson.fromJson(req.getReader(), Customer.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Manager manager = objectMapper.readValue(req.getReader(), Manager.class);
            Manager editManager = (Manager) req.getSession().getAttribute("editManager");
            editManager.setName(manager.getName());
            editManager.setFamily(manager.getFamily());
            editManager.setPhoneNumber(manager.getPhoneNumber());
            editManager.setEmail(manager.getEmail());
            editManager.setNationalCode(manager.getNationalCode());
            editManager.setAddress(manager.getAddress());
            editManager.setEditing(false);
            managerService.edit(editManager);

            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, manager);
            out.flush();
//            manager.setEditing(false);
//            managerService.edit(manager);

        }catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\": \"Failed to update manager.\"}");
        }
    }
}