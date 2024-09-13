<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BeautySalon</title>
    <jsp:include page="css-include.jsp"/>
</head>
<body style="background:-webkit-linear-gradient(left,#ae7a59,#507c6c,#213d26)">


<br><br><br>

<div class="container">
    <div class="row">
        <div >
            <a href="./admin/panel.jsp">
                <img class="col-12 col-md-9 col-lg-3" style=" border-radius: 15%; margin:3%;" src="./assets/images/admin-btn1.jpg" usemap="#area">
            </a>


            <a href="./managers/manager-register.jsp">
            <img class="col-12 col-md-9 col-lg-3" style=" border-radius: 15%; margin:3%;" src="./assets/images/manager-btn1.jpg" usemap="#area">
            </a>

            <a href="./customers/panel.jsp">
            <img class="col-12 col-md-9 col-lg-3" style=" border-radius: 15%; margin:3%;" src="./assets/images/customer-btn1.jpg" usemap="#area">
            </a>
            </div><br><br>

        <%--<a href="/logout">Logout</a>--%>

        </div>
    </div>

</body>
<jsp:include page="js-include.jsp"/>
</html>
