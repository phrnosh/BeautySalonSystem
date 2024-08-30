<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>salon</title>
    <link rel="stylesheet" href="assets/css/boot.css">
    <link rel="stylesheet" href="assets/css/UI.css">
</head>
<body style="background-color:#f3f3f3">
<br><br><br>
<div class="container">
    <div class="row">
        <div class="register-item col-12 col-md-6 col-lg-6 " style="height: auto;">
            <div class="header-register">
                <h4>اطلاعات سالن شما</h4>
                <br><br><hr style="border-top: 3px solid rgba(0, 0, 0, 0.1); margin-top: -18px;">
                <p>اطلاعات سالن خود را وارد کنید</p>
            </div><br><br>

            <div class="input-register">
                <form action="salon.do" method="post">
<%--                    <input type="text" name="id">--%>
                    <input type="text" style="display: block; width: 80%;" placeholder="نام سالن" name="name">
                    <input type="text" style="display: block; width: 80%;" placeholder="آدرس سالن" name="address">
                    <input type="text" style="display: block; width: 80%;" placeholder="توضیحات" name="description">
                    <input type="submit" class="btn btn-outline-dark" style=" display: block; width: 80%;" value="save">
                </form>

            </div>

            <div class="footer-register">

                <%--                <a href="signUp.jsp" class="btn-reg">ساخت اکانت </a>--%>
<%--                <a href="" class="btn-reg">فراموشی رمز عبور</a>--%>

            </div>

        </div>
    </div>
</div>

<br><br><br><br>

<c:if test="${not empty sessionScope.salonList}">
    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Address</th>
            <th>Description</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="salon" items="${sessionScope.salonList}">
            <tr>
                <td>${salon.id}</td>
                <td>${salon.name}</td>
                <td>${salon.address}</td>
                <td>${salon.descripton}</td>
                <td>
                    <button onclick="editManager(${salon.id})">Edit</button>
                        <%--                    <button onclick="Remove(${person.id})">Remove</button>--%>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>


<c:if test="${empty sessionScope.salonList}">
    <h1>No Content</h1>
</c:if>

</body>
</html>
