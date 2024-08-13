package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Admin;
import com.beautysalon.beautysalonsystem.model.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet("/admin.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class AdminServlet extends HttpServlet {

    Map<String, String> csrfTokens = new HashMap<>();
    @Inject
    private AdminService adminService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Admin admin =
                    Admin
                            .builder()
                            .name(StringEscapeUtils.escapeHtml4(req.getParameter("name")))
                            .family(req.getParameter("family"))
                            .build();
            BeanValidator<Admin> adminValidator = new BeanValidator<>();
            if(adminValidator.validate(admin).isEmpty()) {
                adminService.save(admin);
                resp.sendRedirect("/admin.do");
                log.info("admin saved successfully : " + admin.toString());
            }else{
                throw new Exception("Invalid admin Data !!!");  //error
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
}

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("ADMIN PUT : ");
//           Gson gson = new Gson();
//        gson.fromJson(req.getReader(), Admin.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Admin admin = objectMapper.readValue(req.getReader(), Admin.class);
            admin.setEditing(false);
            adminService.edit(admin);
            resp.getWriter().write("javab");
        }catch (Exception e) {
        }
    }
}
