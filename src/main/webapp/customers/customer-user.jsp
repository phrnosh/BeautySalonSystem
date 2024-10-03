<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your Account</title>

    <jsp:include page="../css-include.jsp"/>

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


<div class="container-fluid d-flex flex-row vh-100 p-0">

    <jsp:include page="/customers/customer-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content d-flex flex-column align-items-center flex-grow-1">

            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>Username</th>
                        <th>Password</th>
                        <th>Role</th>
                        <th>Locked</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <tr>
                        <td>${sessionScope.user.username}</td>
                        <td>${sessionScope.user.password}</td>
                        <td>${sessionScope.user.role.role}</td>
                        <td>${sessionScope.user.locked}</td>

                        <td>
                            <button onclick="editUser('${sessionScope.user.username}')" class="btn btn-primary w-25 mt-4">Edit</button>
                        </td>
                    </tr>

                    </tbody>

                </table>

            </div>


        </div>


        <jsp:include page="/footer.jsp"/>


    </div>


</div>


<script>


    function editUser(username) {
        window.location.replace("/user.do?edit=" +username);
    }


</script>


<jsp:include page="../js-include.jsp"/>

</body>
</html>
