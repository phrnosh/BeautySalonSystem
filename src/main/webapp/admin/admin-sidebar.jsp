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

<%--        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="moderator.do">--%>
<%--            <div class="item-icon w-25"><i class="fa fa-id-badge"></i></div>--%>
<%--            <div class="w-50 d-sm-none d-lg-flex">Moderators</div>--%>
<%--        </a>--%>

<%--        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="moderator.do">--%>
<%--            <div class="item-icon w-25"><i class="fa fa-user-circle"></i></div>--%>
<%--            <div class="w-50 d-sm-none d-lg-flex">Moderators</div>--%>
<%--        </a>--%>

        <% } %>

<%--        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="users.do">--%>
<%--            <div class="item-icon w-25"><i class="fa fa-user-check"></i></div>--%>
<%--            <div class="w-50 d-sm-none d-lg-flex">Account</div>--%>
<%--        </a>--%>

        <a class="item d-flex justify-content-center align-items-center mb-auto ps-lg-4" href="manager.do">
            <div class="item-icon w-25"><i class="fa fa-vcard"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Managers</div>
        </a>

        <a class="item d-flex justify-content-center align-items-center mb-auto ps-lg-4" href="customer.do">
            <div class="item-icon w-25"><i class="fa fa-person"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Customers</div>
        </a>

<%--        <a class="item d-flex justify-content-center align-items-center mb-auto ps-lg-4" href="cinema.do">--%>
<%--            <div class="item-icon w-25"><i class="fa fa-camera-movie"></i></div>--%>
<%--            <div class="w-50 d-sm-none d-lg-flex">Cinemas</div>--%>
<%--        </a>--%>

<%--        <a class="item d-flex justify-content-center align-items-center mb-auto ps-lg-4" href="show.do">--%>
<%--            <div class="item-icon w-25"><i class="fa fa-theater-masks"></i></div>--%>
<%--            <div class="w-50 d-sm-none d-lg-flex">Shows</div>--%>
<%--        </a>--%>

<%--        <a class="item d-flex justify-content-center align-items-center mb-auto ps-lg-4" href="ticket.do">--%>
<%--            <div class="item-icon w-25"><i class="fa fa-ticket"></i></div>--%>
<%--            <div class="w-50 d-sm-none d-lg-flex">Tickets</div>--%>
<%--        </a>--%>

<%--        <a class="item d-flex justify-content-center align-items-center mb-auto ps-lg-4" href="payment.do">--%>
<%--            <div class="item-icon w-25"><i class="fa fa-money-bill-transfer"></i></div>--%>
<%--            <div class="w-50 d-sm-none d-lg-flex">Payments</div>--%>
<%--        </a>--%>

        <a class="item d-flex justify-content-center align-items-center mb-auto ps-lg-4" href="bank.do">
            <div class="item-icon w-25"><i class="fa fa-bank"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Banks</div>
        </a>

<%--        <a class="item d-flex justify-content-center align-items-center mb-auto ps-lg-4" href="support.do">--%>
<%--            <div class="item-icon w-25"><i class="fa fa-user-headset"></i></div>--%>
<%--            <div class="w-50 d-sm-none d-lg-flex">Support</div>--%>
<%--        </a>--%>

        <a class="item d-flex justify-content-center align-items-center ps-lg-4" href="logout.do">
            <div class="item-icon w-25"><i class="fa fa-sign-out"></i></div>
            <div class="w-50 d-sm-none d-lg-flex">Exit</div>
        </a>


    </div>

</div>