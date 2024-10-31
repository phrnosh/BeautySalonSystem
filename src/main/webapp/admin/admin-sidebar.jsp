<%@ page import="com.beautysalon.beautysalonsystem.model.entity.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="side-bar d-sm-none d-md-flex col-md-1 col-lg-2">

    <div class="items d-flex flex-column w-100 pt-5 justify-content-around ">

        <%
            User loggedUser = (User) session.getAttribute("user");
            if (loggedUser.getRole().getRole().equals("admin")) {
        %>

        <div class="d-flex w-100 mb-5 justify-content-center">
            <c:choose>
                <c:when test="${not empty sessionScope.loggedAdmin.attachments}">
                    <img class="rounded-circle bg-white" src="${sessionScope.loggedAdmin.attachments.get(0).fileName}" alt="Admin Image" height="80px" width="80px">
                </c:when>
                <c:otherwise>
                    No Image
                </c:otherwise>
            </c:choose>
        </div>

        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="admin.do">
            <div class="item-icon w-25"><i class="fa fa-user-circle"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Admins</div>
        </a>

        <% } %>

        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="user.do">
            <div class="item-icon w-25" style="text-align: center;"><i class="fa fa-user-check"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Account</div>
        </a>

        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="manager.do">
            <div class="item-icon w-25" style="text-align: center;"><i class="fa fa-vcard"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Managers</div>
        </a>

        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="customer.do">
            <div class="item-icon w-25" style="text-align: center;"><i class="fa fa-person"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Customers</div>
        </a>

        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="salon.do">
            <div class="item-icon w-25" style="text-align: center;"><i class="fa "></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Salons</div>
        </a>

        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="services.do">
            <div class="item-icon w-25" style="text-align: center;"><i class="fa fa-brush"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Services</div>
        </a>

        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="booking.do">
            <div class="item-icon w-25"><i class="fa "></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Booking</div>
        </a>

<%--        <a class="item d-flex justify-content-center align-items-center mb-auto ps-lg-4" href="bank.do">--%>
<%--            <div class="item-icon w-25"><i class="fa fa-bank"></i></div>--%>
<%--            <div class="w-50 d-sm-none d-lg-flex">Banks</div>--%>
<%--        </a>--%>

        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="logout.do">
            <div class="item-icon w-25"><i class="fa fa-sign-out"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Exit</div>
        </a>


    </div>

</div>