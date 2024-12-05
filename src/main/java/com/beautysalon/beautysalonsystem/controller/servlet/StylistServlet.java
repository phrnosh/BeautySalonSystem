package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.*;
import com.beautysalon.beautysalonsystem.model.entity.enums.FileType;
import com.beautysalon.beautysalonsystem.model.service.*;
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
import java.time.LocalDateTime;
import java.util.*;


//TODO:Multipart
@Slf4j
@WebServlet(urlPatterns = "/stylist.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class StylistServlet extends HttpServlet {

    Map<String, String> csrfTokens = new HashMap<>();

    @Inject
    private StylistService stylistService;

    @Inject
    private ManagerService managerService;

    @Inject
    private AttachmentService attachmentService;

    @Inject
    private RoleService roleService;

    @Inject
    private SalonService salonService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {


            User user = (User) req.getSession().getAttribute("user");
            Manager manager = managerService.findByUsername(user.getUsername());

            if (req.getParameter("cancel") != null) {
                Stylist editingStylist = stylistService.findById(Long.parseLong(req.getParameter("cancel")));
                editingStylist.setEditing(false);
                stylistService.edit(editingStylist);
                resp.sendRedirect("/stylist.do");
                return;
            }


            if (req.getParameter("edit") != null) {
                Stylist editingStylist = stylistService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingStylist.isEditing()) {
                    editingStylist.setEditing(true);
                    stylistService.edit(editingStylist);
                    req.getSession().setAttribute("editingStylist", editingStylist);
                    req.getRequestDispatcher("/stylists/stylist-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/stylist.do");
                }
            } else {
                List<Stylist> stylists = manager.getSalon().getStylists();
                List<StylistVO> stylistVOList = new ArrayList<>();
                for (Stylist stylist : stylists) {
                    StylistVO stylistVO = new StylistVO(stylist);
                    stylistVOList.add(stylistVO);
                }

                req.getSession().setAttribute("stylists", stylistVOList);
                req.getRequestDispatcher("/stylists/stylist.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/stylist.do");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            User user = (User) req.getSession().getAttribute("user");
            Manager manager = managerService.findByUsername(user.getUsername());
            Salon salon = manager.getSalon();

            if (req.getPart("newImage") != null) {
                Stylist editingStylist = (Stylist) req.getSession().getAttribute("editingStylist");

                for (Attachment attachment : editingStylist.getAttachments()) {
                    attachmentService.remove(attachment.getId());
                }
                editingStylist.getAttachments().clear();


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

                editingStylist.addAttachment(attachment);
                editingStylist.setEditing(false);
                stylistService.edit(editingStylist);

                log.info("Stylist image changed successfully-ID : " + editingStylist.getId());
                resp.sendRedirect("/stylist.do");


            } else {

//                TODO: Stylist Role???
                Role role = roleService.FindByRole("customer");

                User user1 =
                        User
                                .builder()
                                .username(req.getParameter("username"))
                                .password(req.getParameter("password"))
                                .role(role)
                                .locked(false)
                                .deleted(false)
                                .build();

                //todo
                System.out.println("inside try block");
                Stylist stylist =
                        Stylist
                                .builder()
                                .name(req.getParameter("name").toUpperCase())
                                .family(req.getParameter("family").toUpperCase())
                                .phoneNumber(req.getParameter("phoneNumber"))
                                .email(req.getParameter("email"))
                                .nationalCode(req.getParameter("nationalCode"))
                                .address(req.getParameter("address"))
                                .career(req.getParameter("career"))
                                .user(user1)
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

                    stylist.addAttachment(attachment);
                }

                BeanValidator<Stylist> stylistValidator = new BeanValidator<>();
                if (stylistValidator.validate(stylist).isEmpty()) {
                    stylistService.save(stylist);
                    salon.addStylist(stylist);
                    salonService.edit(salon);
                    log.info("Stylist saved successfully : " + stylist.getFamily());
                    resp.sendRedirect("/stylist.do");
                } else {
                    String errorMessage = "Invalid Stylist Data !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/stylist.do");
                }
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/stylist.do");
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
