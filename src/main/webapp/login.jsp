<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="assets/css/boot.css">
    <link rel="stylesheet" href="assets/css/UI.css">
<%--    <jsp:include page="css-include.jsp"/>--%>
</head>
<body style="background-color:#f3f3f3">

<%--<jsp:include page="navbar.jsp"/>--%>

<br><br><br>
<div class="container">
    <div class="row">
        <div class="register-item col-12 col-md-6 col-lg-6 ">
            <div class="header-register">
                <h4>ورود</h4>
                <br><br><hr style="border-top: 3px solid rgba(0, 0, 0, 0.1); margin-top: -18px;">
                <p>با وارد کردن اطلاعات زیر وارد پنل خود شوید</p>
            </div><br><br>

            <div class="input-register text-center mx-auto">
                <form action="j_security_check" method="post">
                    <input class="mb-2" type="text" style="display: block; width: 80%; margin: 0 auto;" placeholder="نام کاربری را وارد کنید" name="j_username">
                    <input class="mb-4" type="password" style="display: block; width: 80%; margin: 0 auto;" placeholder="رمز عبور را وارد کنید" name="j_password">
                    <input type="submit" class="btn btn-outline-dark" style="display: block; width: 80%; margin: 0 auto;" value="Login">
                </form>
            </div>

            <div class="footer-register text-center mx-auto">
                <a href="salonHome.do" class="btn-reg">Back</a>
                <a href="user.do" class="btn-reg">فراموشی رمزعبور</a>
                <a href="sign-up.jsp" class="btn-reg">ساخت اکانت</a>
            </div>

        </div>
    </div>
</div>

<br><br><br><br>

</body>
<jsp:include page="js-include.jsp"/>
</html>

