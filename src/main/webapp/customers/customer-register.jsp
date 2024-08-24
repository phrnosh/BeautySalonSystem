<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer</title>
    <link rel="stylesheet" href="./assets/css/boot.css">
    <link rel="stylesheet" href="./assets/css/UI.css">
    <%--    <jsp:include page="css-include.jsp"/>--%>
</head>
<body style="background-color:#f3f3f3">
<jsp:include page="/./navbar.jsp"/>

<br><br><br>
<div class="container">
    <div class="row">
        <div class="register-item col-12 col-md-6 col-lg-6 ">
            <div class="header-register">
                <h4>ورود</h4>
                <br><br><hr style="border-top: 3px solid rgba(0, 0, 0, 0.1); margin-top: -18px;">
                <p>با وارد کردن اطلاعات زیر وارد پنل خود شوید</p>
            </div><br><br>

            <div class="input-register">

<%--                <form action="j_security_check" method="post">--%>
<%--                    <input type="text" style="display: block; width: 80%;" placeholder="نام کاربری را وارد کنید" name="j_username">--%>
<%--                    <input type="password" style="display: block; width: 80%;" placeholder="رمز عبور را وارد کنید" name="j_password">--%>
<%--                    <input type="submit" class="btn btn-outline-dark" style=" display: block; width: 80%;" value="ورود">--%>
<%--                </form>--%>

                <form action="/customer.do" method="post">
                    <input type="text" name="name">
                    <input type="text" name="family">
                    <input type="text" name="username">
                    <input type="password" name="password">
                    <input type="text" name="email">
                    <input type="tel" name="phone">
                    <input type="text" name="national_id">
                    <select name="status" id="">
                        <option value="">New</option>
                        <option value="">Active</option>
                        <option value="">Blocked</option>
                        <option value="">Banned</option>
                    </select>


                    <input type="submit" value="save">
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



<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Family</th>
        <th>Username</th>
        <th>Password</th>
        <th>Email</th>
        <th>PhoneNumber</th>
        <th>NationalId</th>
        <th>Status</th>
    </tr>
    </thead>


    <tbody></tbody>
</table>
</body>
</html>
