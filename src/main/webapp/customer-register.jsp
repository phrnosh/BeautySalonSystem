<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
      <title>Customer</title>
 </head>
 <body>

 <h4>اکانت مشتری </h4>

 <br><br><hr style="border-top: 3px solid rgba(0, 0, 0, 0.1); margin-top: -18px;">
<p>با وارد کردن اطلاعات زیر اکانت خود را بسازید</p>
</div><br><br>

<form action="j_security_check" method="post">
    <input type="text" style="display: block; width: 80%;" placeholder="نام خود را وارد کنید" name="j_name">
    <input type="text" style="display: block; width: 80%;" placeholder="نام خانوادگی را وارد کنید" name="j_family">
    <input type="password" style="display: block; width: 80%;" placeholder="یوزرنیم را وارد کنید" name="j_username">
    <input type="password" style="display: block; width: 80%;" placeholder="رمز عبور را وارد کنید" name="j_password">
    <input type="password" style="display: block; width: 80%;" placeholder="ایمیل را وارد کنید" name="j_email">
    <input type="password" style="display: block; width: 80%;" placeholder="شماره تلفن را وارد کنید" name="j_phone">
    <input type="password" style="display: block; width: 80%;" placeholder="رمز عبور را وارد کنید" name="j_national_id">
    <input type="submit" class="btn btn-outline-Blue" style=" display: block; width: 80%;" value="ذخیره">
    </form>


</body>
</html>
