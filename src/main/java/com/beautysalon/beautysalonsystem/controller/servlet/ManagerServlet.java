package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.*;
import com.beautysalon.beautysalonsystem.model.entity.enums.FileType;
import com.beautysalon.beautysalonsystem.model.entity.enums.UserState;
import com.beautysalon.beautysalonsystem.model.service.AttachmentService;
import com.beautysalon.beautysalonsystem.model.service.ManagerService;
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
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@WebServlet("/manager.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class ManagerServlet extends HttpServlet {

    Map<String, String> csrfTokens = new HashMap<>();

    @Inject
    private ManagerService managerService;

    @Inject
    private AttachmentService attachmentService;

    @Inject
    private RoleService roleService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("manager.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("manager.do\n\n\n\n");


            User user = (User) req.getSession().getAttribute("user");
            String redirectPath = "";

            if (user.getRole().getRole().equals("manager")) {
                ManagerVO managerVO = new ManagerVO(managerService.findByUsername(user.getUsername()));
                req.getSession().setAttribute("manager", managerVO);
                redirectPath = "/managers/manager-main-panel.jsp";
            } else if (user.getRole().getRole().equals("admin")) {
                List<Manager> managerList = managerService.findAll();
                List<ManagerVO> managerVOList = new ArrayList<>();
                for (Manager manager : managerList) {
                    ManagerVO managerVO = new ManagerVO(manager);
                    managerVOList.add(managerVO);
                }
                req.getSession().setAttribute("allManagers", managerVOList);
                redirectPath = "/admin/managers.jsp";
            }


            if (req.getParameter("cancel") != null) {
                Manager editingManager = managerService.findById(Long.parseLong(req.getParameter("cancel")));
                editingManager.setEditing(false);
                managerService.edit(editingManager);
                resp.sendRedirect("/manager.do");
                return;
            }

            if (req.getParameter("edit") != null) {
                Manager editingManager = managerService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingManager.isEditing()) {
                    editingManager.setEditing(true);
                    managerService.edit(editingManager);
                    req.getSession().setAttribute("editingManager", editingManager);
                    req.getRequestDispatcher("/managers/manager-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/manager.do");
                }
            } else {
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/manager.do");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            if (req.getPart("newImage") != null) {
                Manager editingManager = (Manager) req.getSession().getAttribute("editingManager");

                for (Attachment attachment : editingManager.getAttachments()) {
                    attachmentService.remove(attachment.getId());
                }
                editingManager.getAttachments().clear();


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

                editingManager.addAttachment(attachment);
                editingManager.setEditing(false);
                managerService.edit(editingManager);
                log.info("Manager image changed successfully-ID : " + editingManager.getId());
                resp.sendRedirect("/manager.do");


            } else {


                Role role = (Role) roleService.FindByRole("manager");

                User user =
                        User
                                .builder()
                                .username(req.getParameter("username"))
                                .password(req.getParameter("password"))
                                .role(role)
                                .locked(false)
                                .deleted(false)
                                .build();


                Manager manager =
                        Manager
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
                            .filename(relativePath)
                            .fileType(FileType.Jpg)
                            .fileSize(filePart.getSize())
                            .build();

                    manager.addAttachment(attachment);
                }

                BeanValidator<Manager> managerValidator = new BeanValidator<>();
                if (managerValidator.validate(manager).isEmpty()) {
                    managerService.save(manager);
                    log.info("Manager saved successfully : " + manager.getFamily());
                    resp.sendRedirect("/manager.do");
                } else {
                    String errorMessage = "Invalid Manager Data !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/manager.do");
                }
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/manager.do");
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        Manager managerAb;
        try {

            managerAb = objectMapper.readValue(req.getInputStream(), Manager.class);
            Manager editingManager = (Manager) req.getSession().getAttribute("editingManager");
            editingManager.setEditing(false);
            managerService.edit(editingManager);

            editingManager.setName(managerAb.getName().toUpperCase());
            editingManager.setFamily(managerAb.getFamily().toUpperCase());
            editingManager.setPhoneNumber(managerAb.getPhoneNumber());
            editingManager.setEmail(managerAb.getEmail());
            editingManager.setNationalCode(managerAb.getNationalCode());
            editingManager.setAddress(managerAb.getAddress());

            BeanValidator<Manager> managerValidator = new BeanValidator<>();
            if (managerValidator.validate(editingManager).isEmpty()) {
                managerService.edit(editingManager);
                log.info("Manager updated successfully : " + editingManager.getId());

                resp.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = resp.getWriter();
                objectMapper.writeValue(out, managerAb);
                out.flush();
            }else {
                log.error("Invalid Manager Data For Update !!!");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                PrintWriter out = resp.getWriter();
                out.write("{\"message\": \"Invalid Manager Data.\"}");
                out.flush();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update manager.\"}");
            out.flush();
        }
    }
}