package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.*;
import com.beautysalon.beautysalonsystem.model.entity.enums.FileType;
import com.beautysalon.beautysalonsystem.model.service.AttachmentService;
import com.beautysalon.beautysalonsystem.model.service.CustomerService;
import com.beautysalon.beautysalonsystem.model.service.ManagerService;
import com.beautysalon.beautysalonsystem.model.service.SalonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@WebServlet(urlPatterns = "/salon.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class SalonServlet extends HttpServlet {
    Map<String, String> csrfTokens = new HashMap<>();

    @Inject
    private ManagerService managerService;

    @Inject
    private SalonService salonService;

    @Inject
    private AttachmentService attachmentService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("salon.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("salon.do\n\n\n\n");


            User user = (User) req.getSession().getAttribute("user");
            String redirectPath = "";

            if (user.getRole().getRole().equals("manager")) {
                ManagerVO managerVO = (ManagerVO) req.getSession().getAttribute("manager");
                Manager manager = managerService.findById(managerVO.getId());
                SalonVO salonVO = new SalonVO(managerService.findSalonByManagerId(manager.getId()));
                req.getSession().setAttribute("salon", salonVO);
                redirectPath = "/managers/manager-salon.jsp";
            }  else if (user.getRole().getRole().equals("admin") || user.getRole().getRole().equals("moderator")){
                List<SalonVO> salonVOList = new ArrayList<>();
                List<Salon> salonList = salonService.findAll();
                for (Salon salon : salonList) {
                    SalonVO salonVO = new SalonVO(salon);
                    salonVOList.add(salonVO);
                }
                req.getSession().setAttribute("allSalons", salonVOList);
                redirectPath = "/salon/salon.jsp";
            }

            if (req.getParameter("cancel") != null) {
                Salon editingSalon = salonService.findById(Long.parseLong(req.getParameter("cancel")));
                editingSalon.setEditing(false);
                salonService.edit(editingSalon);
                resp.sendRedirect("/salon.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Salon editingSalon = salonService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingSalon.isEditing()) {
                    editingSalon.setEditing(true);
                    salonService.edit(editingSalon);
                    req.getSession().setAttribute("editingSalon", editingSalon);
                    req.getRequestDispatcher("/managers/manager-salon-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/salon.do");
                }
            } else {
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
//            resp.sendRedirect("/salon.do");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            if (req.getPart("newImage") != null) {
                Salon editingSalon = (Salon) req.getSession().getAttribute("editingSalon");

                for (Attachment attachment : editingSalon.getAttachments()) {
                    attachmentService.remove(attachment.getId());
                }
                editingSalon.getAttachments().clear();


                Part filePart = req.getPart("newImage");

                String applicationPath = req.getServletContext().getRealPath("");

                String uploadDirectory = applicationPath + "uploads";
                File uploadDir = new File(uploadDirectory);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String fileName = filePart.getSubmittedFileName();
                String filePath = uploadDirectory + File.separator + fileName;
                String relativePath = "/uploads/" + fileName;

                filePart.write(filePath);


                Attachment attachment = Attachment.builder()
                        .attachTime(LocalDateTime.now())
                        .filename(relativePath)
                        .fileType(FileType.Jpg)
                        .fileSize(filePart.getSize())
                        .build();

                editingSalon.addAttachment(attachment);
                editingSalon.setEditing(false);
                salonService.edit(editingSalon);
                log.info("Salon image changed successfully-ID : " + editingSalon.getId());
                resp.sendRedirect("/salon.do");

            } else {

                Salon salon =
                        Salon
                                .builder()
                                .name(req.getParameter("name").toUpperCase())
                                .status(Boolean.parseBoolean(req.getParameter("status")))
                                .description(req.getParameter("description"))
                                .address(req.getParameter("address"))
                                .deleted(false)
                                .build();

                Part filePart = req.getPart("image");

                if (filePart != null && filePart.getSize() > 0) {
                    String applicationPath = req.getServletContext().getRealPath("");

                    String uploadDirectory = applicationPath + "uploads";
                    File uploadDir = new File(uploadDirectory);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }


                    String fileName = filePart.getSubmittedFileName();
                    String filePath = uploadDirectory + File.separator + fileName;
                    String relativePath = "/uploads/" + fileName;

                    filePart.write(filePath);

                    Attachment attachment = Attachment.builder()
                            .attachTime(LocalDateTime.now())
                            .filename(relativePath)
                            .fileType(FileType.Jpg)
                            .fileSize(filePart.getSize())
                            .build();

                    salon.addAttachment(attachment);
                }

                BeanValidator<Salon> salonValidator = new BeanValidator<>();

                if (salonValidator.validate(salon).isEmpty()) {
                    Manager manager = managerService.findById(Long.parseLong(req.getParameter("managerId")));
                    manager.setSalon(salon);
                    managerService.edit(manager);
                    log.info("Salon saved successfully-ID : " + salon.getId());
                    resp.sendRedirect("/salon.do");
                } else {
                    String errorMessage = "Invalid Salon Data !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/salon.do");
                }
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
//            resp.sendRedirect("/salon.do");

        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        Salon salonAb;

        try {
            salonAb = objectMapper.readValue(req.getInputStream(), Salon.class);
            Salon editingSalon = (Salon) req.getSession().getAttribute("editingSalon");
            editingSalon.setEditing(false);
            salonService.edit(editingSalon);

            editingSalon.setName(salonAb.getName().toUpperCase());
            editingSalon.setStatus(salonAb.isStatus());
            editingSalon.setDescription(salonAb.getDescription());
            editingSalon.setAddress(salonAb.getAddress());

            BeanValidator<Salon> salonValidator = new BeanValidator<>();

            if (salonValidator.validate(editingSalon).isEmpty()) {
                salonService.edit(editingSalon);
                log.info("Salon updated successfully : " + editingSalon.getId());

                resp.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = resp.getWriter();
                objectMapper.writeValue(out, salonAb);
                out.flush();
            } else {
                log.error("Invalid Salon Data For Update !!!");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                PrintWriter out = resp.getWriter();
                out.write("{\"message\": \"Invalid Salon Data.\"}");
                out.flush();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update salon.\"}");
            out.flush();
        }
    }
}