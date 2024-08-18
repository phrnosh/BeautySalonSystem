<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 8/18/2024
  Time: 3:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>درست کردن اکانت</title>
</head>
<body>

<h4>ساخت اکانت</h4>
<br><br><hr style="border-top: 3px solid rgba(0, 0, 0, 0.1); margin-top: -18px;">
<p>با وارد کردن اطلاعات زیر اکانت خود را بسازید</p>
</div><br><br>

<form action="j_security_check" method="post">
    <input type="text" style="display: block; width: 80%;" placeholder="نام کاربری را وارد کنید" name="j_username">
    <input type="password" style="display: block; width: 80%;" placeholder="رمز عبور را وارد کنید" name="j_password">
    <input type="submit" class="btn btn-outline-Blue" style=" display: block; width: 80%;" value="ذخیره">
</form>



</body>
</html>
