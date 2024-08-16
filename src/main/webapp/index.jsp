<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BeautySalon</title>
    <jsp:include page="css-include.jsp"/>
</head>
<body style="background:-webkit-linear-gradient(left,#ae7a59,#507c6c,#213d26)">
<%--<h1>Welcome</h1>--%>

<%--<a href="./admin/panel.jsp">Admin Panel</a><br><br>--%>
<%--<a href="./managers/panel.jsp">Manager Panel</a><br><br>--%>
<%--<a href="./customers/panel.jsp">Customer Panel</a><br><br>--%>
<%--<a href="/logout">Logout</a>--%>

<br><br><br>

<div class="container">
    <div class="row">
        <div >
            <img class="col-12 col-md-9 col-lg-3" style=" border-radius: 15%; margin:3%;" src="./assets/images/admin-btn1.jpg" usemap="#area">
            <map name="area"><area shape="default" href="./admin/panel.jsp"></map>

            <img class="col-12 col-md-9 col-lg-3" style=" border-radius: 15%; margin:3%;" src="./assets/images/manager-btn1.jpg" usemap="#area">
            <map name="area"><area shape="default" href="./admin/panel.jsp"></map>

            <img class="col-12 col-md-9 col-lg-3" style=" border-radius: 15%; margin:3%;" src="./assets/images/customer-btn1.jpg" usemap="#area">
            <map name="area"><area shape="default" href="./admin/panel.jsp"></map>
            </div><br><br>




        </div>
    </div>
</div>

</body>
<jsp:include page="js-include.jsp"/>
</html>
