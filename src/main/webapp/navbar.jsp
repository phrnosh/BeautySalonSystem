<%@ page import="com.beautysalon.beautysalonsystem.model.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<br>--%>
<%--<nav class="navbar navbar-expand-lg navbar-light " id="nav-menu" style="background-color:#ffffff54; width: 100%;">--%>
<%--    <button style="margin-bottom: 20px;" class="navbar-toggler" type="button" data-toggle="collapse"--%>
<%--            data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"--%>
<%--            aria-label="Toggle navigation">--%>
<%--        <span class="navbar-toggler-icon"></span>--%>
<%--    </button>--%>

<%--    <div class="collapse navbar-collapse" id="navbarSupportedContent"--%>
<%--         style=" margin-left: -16px; margin-right: -22px;">--%>
<%--        <div class="container" >--%>
<%--            <ul class="navbar-nav ">--%>
<%--                <li class="nav-item d-block d-lg-none">--%>
<%--                    <input type="search" width="100%" placeholder="جستجو..." class="serchbox-mobile">--%>
<%--                </li>--%>
<%--                <li class="nav-item active" style="padding-right: 1em; padding-left: 1em;">--%>
<%--                    <a class="nav-link btn-hover" href="#">خانه<span class="sr-only"></span></a>--%>
<%--                </li>--%>
<%--                <li class="nav-item dropdown">--%>
<%--                    <a class="nav-link dropdown-toggle btn-hover" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="padding-right: 1em;">--%>
<%--                        محصولات--%>
<%--                    </a>--%>
<%--                    <div class="dropdown-menu dropdown-menu-right text-right" aria-labelledby="navbarDropdown">--%>
<%--                        <a class="dropmenu" href="services.do">آرایشی</a>--%>
<%--                        <div class="dropdown-divider"></div>--%>
<%--                        <a class="dropmenu" href="#">ترمیم</a>--%>
<%--                        <!-- <div class="dropdown-divider"></div> -->--%>
<%--                        <a class="dropmenu" href="#">مو</a>--%>
<%--                    </div>--%>
<%--                </li>--%>
<%--                <li class="nav-item" style="padding-right: 1em;">--%>
<%--                    <a class="nav-link btn-hover" href="#">سالن ها</a>--%>
<%--                </li>--%>
<%--                <li class="nav-item" style="padding-right: 1em;">--%>
<%--                    <a class="nav-link btn-hover" href="#">درباره ما</a>--%>
<%--                </li>--%>
<%--            </ul>--%>
<%--            <!-- <form class="form-inline my-2 my-lg-0">--%>
<%--                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">--%>
<%--                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>--%>
<%--            </form> -->--%>
<%--        </div>--%>
<%--    </div>--%>

<%--</nav>--%>













<div class="nav d-sm-none d-md-flex justify-content-center">

    <%
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser != null) {
    %>
    <!-- Display the username if logged in -->
    <div class="item d-flex justify-content-center align-items-center m-auto ">

        <a class="item d-flex justify-content-center align-items-center m-auto " href="postLogin.do">
            <div class="item-icon w-25"><i class="fa fa-id-badge"></i></div>
            <div class="w-50 d-sm-none d-lg-flex m-auto">Panel</div>
        </a>

        <div class="w-50 d-sm-none d-lg-flex text-light" style="margin-left: 20px">User:<%= loggedUser.getUsername() %></div>

    </div>




    <% } else { %>
    <!-- Display the login link if not logged in -->
    <a class="item d-flex justify-content-center align-items-center m-auto " href="postLogin.do">
        <div class="item-icon w-25"><i class="fa fa-sign-in"></i></div>
        <div class="w-50 d-sm-none d-lg-flex m-auto">Log_in</div>
    </a>
    <% } %>

    <a class="item d-flex justify-content-center align-items-center m-auto " href="search.do">
        <div class="item-icon w-25"><i class="fa fa-search"></i></div>
        <div class="w-50 d-sm-none d-lg-flex m-auto">Search</div>
    </a>

    <a class="item d-flex justify-content-center align-items-center m-auto " href="salonHome.do">
        <div class="item-icon w-25"><i class="fa fa-home"></i></div>
        <div class="w-50 d-sm-none d-lg-flex m-auto">Home</div>
    </a>

</div>

