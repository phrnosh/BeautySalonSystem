<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer</title>
    <link rel="stylesheet" href="/assets/css/boot.css">
    <link rel="stylesheet" href="/assets/css/UI.css">
    <%--    <jsp:include page="css-include.jsp"/>--%>
</head>
<body style="background-color:#f3f3f3">
<jsp:include page="/./navbar.jsp"/>

<br><br><br>
<div class="container">
    <div class="row">
        <div class="register-item col-12 col-md-6 col-lg-6 "  style="height: auto;">
            <div class="header-register">
                <h4>ساخت اکانت مشتری</h4>
                <br><br><hr style="border-top: 3px solid rgba(0, 0, 0, 0.1); margin-top: -18px;">
                <p>با وارد کردن اطلاعات زیر وارد پنل خود را بسازید</p>
            </div><br><br>

            <div class="input-register">

                <form action="customer.do" method="post">
                    <input type="text" style="display: block; width: 80%;" placeholder="نام" name="name">
                    <input type="text" style="display: block; width: 80%;" placeholder="نام خانوادگی" name="family">
                    <input type="text" style="display: block; width: 80%;" placeholder="ایمیل" name="email">
                    <input type="tel" style="display: block; width: 80%;" placeholder="تلفن همراه" name="phone">
                    <input type="text" style="display: block; width: 80%;" placeholder="کدملی" name="national_id">
                    <select name="status" style="display: block; width: 80%;" id="">
                        <option value="">New</option>
                        <option value="">Active</option>
                        <option value="">Blocked</option>
                        <option value="">Banned</option>
                    </select>
                    <input type="text" style="display: block; width: 80%;" placeholder="نام کاربری" name="username">
                    <input type="password" style="display: block; width: 80%;" placeholder="رمز عبور " name="password">
                    <input type="submit" class="btn btn-outline-dark" style=" display: block; width: 80%;" value="save">
                </form>
            </div>

            <div class="footer-register">

                <a href="" class="btn-reg">ورود به سایت</a>
                <a href="" class="btn-reg">فراموشی رمز عبور</a>

            </div>

        </div>
    </div>
</div>

<br><br><br><br>


<c:if test="${not empty sessionScope.customerList}">
    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Family</th>
            <th>Email</th>
            <th>PhoneNumber</th>
            <th>NationalId</th>
            <th>Status</th>
            <th>Username</th>
            <th>Password</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="customer" items="${sessionScope.customerList}">
            <tr>
                <td>${customer.id}</td>
                <td>${customer.name}</td>
                <td>${customer.family}</td>
                <td>${customer.email}</td>
                <td>${customer.phone}</td>
                <td>${customer.national_id}</td>
                <td>${customer.status}</td>
                <td>${customer.username}</td>
                <td>${customer.password}</td>

                <td>
                    <button onclick="editManager(${customer.id})">Edit</button>
                        <%--                    <button onclick="Remove(${person.id})">Remove</button>--%>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>


<c:if test="${empty sessionScope.customerList}">
    <h1>No Content</h1>
</c:if>

</body>
</html>
