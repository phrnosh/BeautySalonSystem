package com.beautysalon.beautysalonsystem.controller.servlet;


import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.Attachment;
import com.beautysalon.beautysalonsystem.model.entity.Moderator;
import com.beautysalon.beautysalonsystem.model.entity.Role;
import com.beautysalon.beautysalonsystem.model.entity.User;
import com.beautysalon.beautysalonsystem.model.entity.enums.FileType;
import com.beautysalon.beautysalonsystem.model.service.AttachmentService;
import com.beautysalon.beautysalonsystem.model.service.ModeratorService;
import com.beautysalon.beautysalonsystem.model.service.RoleService;
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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Enumeration;

@Slf4j
@WebServlet(urlPatterns = "/moderator.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class ModeratorServlet extends HttpServlet {

    @Inject
    private ModeratorService moderatorService;

    @Inject
    private RoleService roleService;

    @Inject
    private AttachmentService attachmentService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("moderator.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("moderator.do\n\n\n\n");


            User user = (User) req.getSession().getAttribute("user");

            if (user.getRole().getRole().equals("moderator")) {
                Moderator loggedModerator = moderatorService.findByUsername(user.getUsername());
                req.getSession().setAttribute("loggedModerator", loggedModerator);
            }


            if (req.getParameter("cancel") != null) {
                Moderator editingModerator = moderatorService.findById(Long.parseLong(req.getParameter("cancel")));
                editingModerator.setEditing(false);
                moderatorService.edit(editingModerator);
                resp.sendRedirect("/moderator.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Moderator editingModerator = moderatorService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingModerator.isEditing()) {
                    editingModerator.setEditing(true);
                    moderatorService.edit(editingModerator);
                    req.getSession().setAttribute("editingModerator", editingModerator);
                    req.getRequestDispatcher("/moderators/moderator-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/moderator.do");
                }
            } else {
                req.getSession().setAttribute("allModerators", moderatorService.findAll());
                req.getRequestDispatcher("/moderators/moderator-panel.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/moderator.do");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            if (req.getPart("newImage") != null) {
                Moderator editingModerator = (Moderator) req.getSession().getAttribute("editingModerator");

                for (Attachment attachment : editingModerator.getAttachments()) {
                    attachmentService.remove(attachment.getId());
                }
                editingModerator.getAttachments().clear();


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
                        .fileName(relativePath)
                        .fileType(FileType.Jpg)
                        .fileSize(filePart.getSize())
                        .build();

                editingModerator.addAttachment(attachment);
                editingModerator.setEditing(false);
                moderatorService.edit(editingModerator);
                log.info("Moderator image changed successfully-ID : " + editingModerator.getId());
                resp.sendRedirect("/moderator.do");


            } else {


                Role role = roleService.FindByRole("moderator");

                User user =
                        User
                                .builder()
                                .username(req.getParameter("username"))
                                .password(req.getParameter("password"))
                                .role(role)
                                .locked(false)
                                .deleted(false)
                                .build();


                Moderator moderator =
                        Moderator
                                .builder()
                                .name(req.getParameter("name").toUpperCase())
                                .family(req.getParameter("family").toUpperCase())
                                .phoneNumber(req.getParameter("phoneNumber"))
                                .email(req.getParameter("email"))
                                .nationalCode(req.getParameter("nationalCode"))
                                .address(req.getParameter("address"))
                                .user(user)
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
                            .fileName(relativePath)
                            .fileType(FileType.Jpg)
                            .fileSize(filePart.getSize())
                            .build();

                    moderator.addAttachment(attachment);
                }

                BeanValidator<Moderator> moderatorValidator = new BeanValidator<>();
                if (moderatorValidator.validate(moderator).isEmpty()) {
                    moderatorService.save(moderator);
                    log.info("Moderator saved successfully : " + moderator.getFamily());
                    resp.sendRedirect("/moderator.do");
                } else {
                    String errorMessage = "Invalid Moderator Data !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/moderator.do");
                }
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/moderator.do");
        }

    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        Moderator moderatorAb;
        try {

            moderatorAb = objectMapper.readValue(req.getInputStream(), Moderator.class);
            Moderator editingModerator = (Moderator) req.getSession().getAttribute("editingModerator");
            editingModerator.setEditing(false);
            moderatorService.edit(editingModerator);

            editingModerator.setName(moderatorAb.getName().toUpperCase());
            editingModerator.setFamily(moderatorAb.getFamily().toUpperCase());
            editingModerator.setNationalCode(moderatorAb.getNationalCode());
            editingModerator.setPhoneNumber(moderatorAb.getPhoneNumber());
            editingModerator.setEmail(moderatorAb.getEmail());
            editingModerator.setAddress(moderatorAb.getAddress());

            BeanValidator<Moderator> moderatorValidator = new BeanValidator<>();
            if (moderatorValidator.validate(editingModerator).isEmpty()) {
                moderatorService.edit(editingModerator);
                log.info("Moderator updated successfully : " + editingModerator.getId());

                resp.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = resp.getWriter();
                objectMapper.writeValue(out, moderatorAb);
                out.flush();
            }else {
                log.error("Invalid Moderator Data For Update !!!");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                PrintWriter out = resp.getWriter();
                out.write("{\"message\": \"Invalid Moderator Data.\"}");
                out.flush();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update moderator.\"}");
            out.flush();
        }
    }
}