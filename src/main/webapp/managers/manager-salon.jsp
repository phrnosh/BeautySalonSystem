<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Services</title>

    <link rel="stylesheet" href="../assets/css/index.css">
    <link rel="stylesheet" href="../assets/css/all.css">
    <link rel="stylesheet" href="../assets/css/UI.css">
    <link rel="stylesheet" href="../assets/css/bootstrap.min.css">

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

        <div class="content flex-column justify-content-center  align-items-center flex-grow-1">

            <div>
                <h4 class="mb-0">${sessionScope.manager.salonName} Salon</h4>
            </div>

            <div class="d-flex justify-content-center p-4 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Status</th>
                        <th>Address</th>
                        <th>Description</th>
                        <th>Image</th>
                        <th>Operation</th>

                    </tr>
                    </thead>

                    <tbody>



                        <tr>
                            <td>${sessionScope.salon.id}</td>
                            <td>${sessionScope.salon.name}</td>
                            <td>${sessionScope.salon.status}</td>
                            <td>${sessionScope.salon.address}</td>
                            <td>${sessionScope.salon.description}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${sessionScope.salon.imageUrl != ''}">
                                        <img src="${sessionScope.salon.imageUrl}" alt="Salon Image" height="80px"
                                             width="80px">
                                    </c:when>
                                    <c:otherwise>
                                        No Image
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <button onclick="setServices(${sessionScope.salon.id})" class="btn btn-secondary w-auto mt-4">Services
                                </button>

                                <button onclick="setTiming(${sessionScope.salon.id})" class="btn btn-secondary w-auto mt-4">
                                    Timing
                                </button>
                                <button onclick="editSalon(${sessionScope.salon.id})" class="btn btn-primary w-auto mt-4">Edit</button>
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
    function editSalon(id) {
        window.location.replace("/salon.do?edit=" + id);
    }

    function setServices(id) {
        window.location.replace("/services.do?salonId=" + id);
    }

    function setTiming(id) {
        window.location.replace("/timing.do?salonId=" + id);
    }

</script>


<jsp:include page="../js-include.jsp"/>

</body>
</html>