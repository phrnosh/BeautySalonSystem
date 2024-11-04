package com.beautysalon.beautysalonsystem.controller.servlet;

import com.beautysalon.beautysalonsystem.controller.exception.ExceptionWrapper;
import com.beautysalon.beautysalonsystem.controller.validation.BeanValidator;
import com.beautysalon.beautysalonsystem.model.entity.*;
import com.beautysalon.beautysalonsystem.model.entity.enums.FileType;
import com.beautysalon.beautysalonsystem.model.entity.enums.ServicesType;
import com.beautysalon.beautysalonsystem.model.service.ManagerService;
import com.beautysalon.beautysalonsystem.model.service.SalonService;
import com.beautysalon.beautysalonsystem.model.service.ServicesService;
import com.beautysalon.beautysalonsystem.model.service.StylistService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = "/services.do")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class ServicesServlet extends HttpServlet {

    @Inject
    private ManagerService managerService;

    @Inject
    private SalonService salonService;

    @Inject
    private ServicesService servicesService;

    @Inject
    private StylistService stylistService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {

            Enumeration<String> attributeNames = req.getSession().getAttributeNames();
            System.out.println("services.do");
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Attribute Name: " + attributeName);
            }
            System.out.println("services.do\n\n\n\n");


            User user = (User) req.getSession().getAttribute("user");


            ManagerVO managervo = null;
            Manager manager = null;
            String redirectPath = "";
            List<Services> salonServices = new ArrayList<>();


            if (user.getRole().getRole().equals("manager")) {
                managervo = (ManagerVO) req.getSession().getAttribute("manager");
                manager = managerService.findById(managervo.getId());
                salonServices = manager.getSalon().getServicesList();
                redirectPath = "/managers/manager-services.jsp";
                List<Services> services = managerService.findServicesByManagerId(manager.getId());
                req.getSession().setAttribute("services", services);
                req.getSession().setAttribute("stylists", manager.getSalon().getStylists());
            }  else if (user.getRole().getRole().equals("admin") || user.getRole().getRole().equals("moderator")){
                if (req.getParameter("salonId") != null) {
                    manager = managerService.findManagerBySalonId(Long.parseLong(req.getParameter("salonId")));
                    ManagerVO managerVO1 = new ManagerVO(manager);
                    req.getSession().setAttribute("manager", managerVO1);
                    redirectPath = "/salon/salon-services.jsp";
                    salonServices = manager.getSalon().getServicesList();
                    List<Services> services = managerService.findServicesByManagerId(manager.getId());
                    req.getSession().setAttribute("services", services);
                    req.getSession().setAttribute("stylists", manager.getSalon().getStylists());
                } else {
                    if (req.getSession().getAttribute("manager") != null) {
                        managervo = (ManagerVO) req.getSession().getAttribute("manager");
                        manager = managerService.findById(managervo.getId());
                        salonServices = manager.getSalon().getServicesList();
                    }
                    redirectPath = "/services/services.jsp";
                }

            }


            if (req.getParameter("cancel") != null) {
                Services editingServices = servicesService.findById(Long.parseLong(req.getParameter("cancel")));
                editingServices.setEditing(false);
                servicesService.edit(editingServices);
                resp.sendRedirect("/services.do");
                return;
            }

            if (req.getParameter("removeFromList") != null) {
                Services servicesToRemove = servicesService.findById(Long.parseLong(req.getParameter("removeFromList")));
                if (servicesToRemove.isAvailable()) {
                    String errorMessage = "The available service can not be removed  !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/services.do");
                } else {
                    // Remove the services and update the salon
                    salonServices.removeIf(services -> services.getId().equals(servicesToRemove.getId()));
                    salonService.edit(manager.getSalon());
                    log.info("Removed Services: " + servicesToRemove.getName());
                    resp.sendRedirect("/services.do");
                }
                return;
            }


            if (req.getParameter("add") != null) {
                Services addingServices = servicesService.findById(Long.parseLong(req.getParameter("add")));

                // Check if the services already exists in the list
                boolean servicesExists = salonServices.stream().anyMatch(services -> services.getId().equals(addingServices.getId()));

                if (servicesExists) {
                    String errorMessage = "The services is already exist in your list  !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/services.do");
                } else {
                    // Add the services to the salon's list and update the database
                    salonServices.add(addingServices);
                    salonService.edit(manager.getSalon());
                    log.info("Added services: " + addingServices.getName());
                    resp.sendRedirect("/services.do");
                }
                return;
            }

            if (req.getParameter("edit") != null) {
                Services editingServices = servicesService.findById(Long.parseLong(req.getParameter("edit")));
                if (!editingServices.isEditing()) {
                    editingServices.setEditing(true);
                    servicesService.edit(editingServices);
                    req.getSession().setAttribute("editingServices", editingServices);
                    req.getRequestDispatcher("/services/services-edit.jsp").forward(req, resp);
                } else {
                    String errorMessage = "Record is editing by another user !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/services.do");
                }
            } else {
                req.getSession().setAttribute("allServices", servicesService.findAll());
                req.getRequestDispatcher(redirectPath).forward(req, resp);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/services.do");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            if (req.getPart("newImage") != null) {
                Services editingServices = (Services) req.getSession().getAttribute("editingServices");

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

                editingServices.addAttachment(attachment);
                editingServices.setEditing(false);
                servicesService.edit(editingServices);
                log.info("Services image changed successfully-ID : " + editingServices.getId());
                resp.sendRedirect("/services.do");

            } else {

                Stylist stylist = stylistService.findById(Long.parseLong(req.getParameter("stylistId")));

                

                Services services =
                        Services
                                .builder()
                                .name(req.getParameter("name"))
                                .stylistName(stylist.getName() + stylist.getFamily())
                                .deleted(false)
                                .available(false)
                                .servicesType(ServicesType.valueOf(req.getParameter("servicesType")))
                                .status(Boolean.parseBoolean((req.getParameter("status"))))
//                                .dateOfModified(LocalDateTime.parse(req.getParameter("releasedDate")))
                                .description(req.getParameter("description"))
                                .build();


                Part filePart = req.getPart("image");

                if (filePart != null && filePart.getSize() > 0) { // Check if file is uploaded
                    // Get the application's absolute path
                    String applicationPath = req.getServletContext().getRealPath("");


                    // Define the path where the file will be saved
                    String uploadDirectory = applicationPath + "uploads";
                    File uploadDir = new File(uploadDirectory);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir(); // Create the uploads directory if it doesn't exist
                    }

                    // Generate a unique file name (to avoid collisions)
                    String fileName = filePart.getSubmittedFileName();
                    String filePath = uploadDirectory + File.separator + fileName;
                    String relativePath = "/uploads/" + fileName; // Path for displaying in JSP

                    // Save the file to the specified directory
                    filePart.write(filePath);

                    // Create and add the attachment
                    Attachment attachment = Attachment.builder()
                            .attachTime(LocalDateTime.now())
                            .filename(relativePath) // Store the relative path for JSP display
                            .fileType(FileType.Jpg) // Assuming JPG, modify as needed
                            .fileSize(filePart.getSize())
                            .build();

                    services.addAttachment(attachment);
                }

                BeanValidator<Services> servicesValidator = new BeanValidator<>();
                if (servicesValidator.validate(services).isEmpty()) {
                    servicesService.save(services);
                    stylist.addServices(services);
                    stylistService.edit(stylist);
                    if (req.getSession().getAttribute("manager") != null) {
                        ManagerVO managerVO = (ManagerVO) req.getSession().getAttribute("manager");
                        Manager manager = managerService.findById(managerVO.getId());
                        Salon salon = manager.getSalon();
                        salon.addServices(services);
                        salonService.edit(salon);
                    }

                    log.info("Services saved successfully-ID : " + services.getId());
                    resp.sendRedirect("/services.do");
                } else {
                    String errorMessage = "Invalid Services Data !!!";
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    log.error(errorMessage);
                    resp.sendRedirect("/services.do");
                }
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.error(ExceptionWrapper.getMessage(e).toString());
            resp.sendRedirect("/services.do");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("doPut");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());

        Services servicesAb;

        try {
            servicesAb = objectMapper.readValue(req.getInputStream(), Services.class);
            Services editingServices = (Services) req.getSession().getAttribute("editingServices");
            editingServices.setEditing(false);
            servicesService.edit(editingServices);

            editingServices.setStylistName(servicesAb.getStylistName().toUpperCase());
//            editingServices.setDateOfModified(servicesAb.getDateOfModified());
            editingServices.setStatus(servicesAb.isStatus());
            editingServices.setServicesType(servicesAb.getServicesType());
            editingServices.setDescription(servicesAb.getDescription());

            BeanValidator<Services> servicesValidator = new BeanValidator<>();
            if (servicesValidator.validate(editingServices).isEmpty()) {
                servicesService.edit(editingServices);
                log.info("Services edited successfully : " + editingServices.toString());

                resp.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = resp.getWriter();
                objectMapper.writeValue(out, servicesAb);
                out.flush();
            } else {
                log.error("Invalid Services Data For Update !!!");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                PrintWriter out = resp.getWriter();
                out.write("{\"message\": \"Invalid Services Data.\"}");
                out.flush();
            }
        } catch (Exception e) {
            log.error(ExceptionWrapper.getMessage(e).toString());

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("{\"message\": \"Failed to update services.\"}");
            out.flush();
        }

    }
}