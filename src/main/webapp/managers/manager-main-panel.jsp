<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager Panel</title>

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

    <jsp:include page="/managers/manager-sidebar.jsp"/>
    <div class="content d-flex flex-column flex-grow-1">
        <jsp:include page="/navbar.jsp"/>

        <div class="content d-flex flex-column  align-items-center flex-grow-1">
            <div class="d-flex justify-content-center p-5 w-100">
                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th hidden="hidden">ID</th>
                        <th>Name</th>
                        <th>Family</th>
                        <th>Username</th>
                        <th>Password</th>
                        <th>National Code</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>Address</th>
                        <th>Image</th>
                    </tr>
                    </thead>

                    <tbody>

                    <tr>
                        <td hidden="hidden">${sessionScope.manager.id}</td>
                        <td>${sessionScope.manager.name}</td>
                        <td>${sessionScope.manager.family}</td>
                        <td>${sessionScope.manager.user.username}</td>
                        <td>${sessionScope.manager.user.password}</td>
                        <td>${sessionScope.manager.nationalCode}</td>
                        <td>${sessionScope.manager.phoneNumber}</td>
                        <td>${sessionScope.manager.email}</td>
                        <td>${sessionScope.manager.address}</td>

                        <td>

                            <c:choose>
                                <c:when test="${sessionScope.manager.imageUrl != ''}">
                                    <img src="${sessionScope.manager.imageUrl}" alt="Manager Image" height="80px" width="80px">
                                </c:when>
                                <c:otherwise>
                                    No Image
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <button onclick="editManager(${sessionScope.manager.id})" class="btn btn-primary w-25 mt-4">Edit</button>

        </div>
        <jsp:include page="/footer.jsp"/>
    </div>

</div>

<script>

    function editManager(id) {
        window.location.replace("/manager.do?edit=" + id);
    }
</script>


<jsp:include page="../js-include.jsp"/>

</body>
</html>