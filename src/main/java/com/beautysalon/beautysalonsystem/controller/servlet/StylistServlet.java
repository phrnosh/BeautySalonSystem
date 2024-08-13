package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Customer;
import com.beautysalon.beautysalonsystem.model.entity.Stylist;
import com.beautysalon.beautysalonsystem.model.service.CustomerService;
import com.beautysalon.beautysalonsystem.model.service.StylistService;
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
@WebServlet("/stylist.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 , // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class StylistServlet extends HttpServlet {

    Map<String, String> csrfTokens = new HashMap<>();

    @Inject
    private StylistService stylistService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Stylist stylist =
                    Stylist
                            .builder()
                            .name(StringEscapeUtils.escapeHtml4(req.getParameter("name")))
                            .family(req.getParameter("family"))
                            .build();

            BeanValidator<Stylist> StylistValidator = new BeanValidator<>();
            if(StylistValidator.validate(stylist).isEmpty()) {
                stylistService.save(stylist);
                resp.sendRedirect("/stylist.do");
                log.info("stylist saved successfully : " + stylist.toString());
            }else{
                throw new Exception("Invalid Stylist Data !!!");  //error
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("STYLIST PUT : ");
//           Gson gson = new Gson();
//        gson.fromJson(req.getReader(), Stylist.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Stylist stylist = objectMapper.readValue(req.getReader(), Stylist.class);
            stylist.setEditing(false);
            stylistService.edit(stylist);
            resp.getWriter().write("javab");
        }catch (Exception e) {
        }
    }

}
