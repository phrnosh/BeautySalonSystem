<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>

    <link rel="stylesheet" href="assets/css/boot.css">
    <link rel="stylesheet" href="assets/css/UI.css">

</head>
<body>

<%
    String errorMessage = (String) session.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
<div class="alert alert-danger">
    <%= errorMessage %>
</div>
<%
        session.removeAttribute("errorMessage");
    }
%>


<br><br><br>
<div class="container d-flex justify-content-center">
    <div class="register-item col-12 col-md-6 col-lg-6 bg-secondary">
        <div class="header-register text-center">
            <h4>Login</h4>
            <br><br><hr style="border-top: 3px solid rgba(0, 0, 0, 0.1); margin-top: -18px;">
            <p>با وارد کردن اطلاعات زیر وارد پنل خود شوید</p>
        </div><br><br>

        <div class="input-register text-center mx-auto">
            <form action="customer.do" method="post">
                <input class="mb-2" type="text" style="display: block; width: 80%;" placeholder=" Name" name="name">
                <input class="mb-2" type="text" style="display: block; width: 80%;" placeholder=" Family" name="family">
                <input class="mb-2" type="text" style="display: block; width: 80%; " placeholder=" Phone Number" name="phoneNumber">
                <input class="mb-2" type="text" style="display: block; width: 80%;" placeholder="username" name="username">
                <input class="mb-2" type="password" style="display: block; width: 80%;" placeholder=" password" name="password">
                <input class="mb-4" type="text" style="display: block; width: 80%; " placeholder=" email" name="email">
                <input type="submit" class="btn btn-outline-dark" style="display: block; width: 80%;" value="save">
            </form>
        </div>

        <div class="footer-register text-center mx-auto">
            <a href="" class="btn-reg">فراموشی رمز عبور</a>
            <a href="postLogin.do" class="btn-reg">Back to Login</a>
        </div>
    </div>
</div>

<br><br><br><br>

</body>

<script src="assets/js/jquery-3.7.1.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/bootstrap.bundle.min.js"></script>

</html>