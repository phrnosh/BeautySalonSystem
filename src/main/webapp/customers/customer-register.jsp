<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer</title>
</head>
<body>
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
