<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer</title>
    <jsp:include page="../css-include.jsp"/>
        <link rel="stylesheet" href="/assets/css/boot.css">
        <link rel="stylesheet" href="/assets/css/UI.css">
</head>
<body style="background-color:rgba(200, 161, 132, 0.64);">

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
        <jsp:include page="/./navbar.jsp"/>

        <div class="content d-flex flex-column align-items-center flex-grow-1">

            <table id="resultTable" border="1" class="table-light w-100">
                <thead>
                <tr>
                    <th hidden="hidden">ID</th>
                    <th>Name</th>
                    <th>Family</th>
                    <th>Phone Number</th>
                    <th>Email</th>
                    <th>Image</th>
                    <th>Operation</th>
                </tr>
                </thead>

                <tbody>

                <tr>
                    <td hidden="hidden">${sessionScope.customer.id}</td>
                    <td>${sessionScope.customer.name}</td>
                    <td>${sessionScope.customer.family}</td>
                    <td>${sessionScope.customer.phoneNumber}</td>
                    <td>${sessionScope.customer.email}</td>

                    <td>
                        <c:choose>
                            <c:when test="${sessionScope.customer.imageUrl != ''}">
                                <img src="${sessionScope.customer.imageUrl}" alt="Customer Image" height="80px"
                                     width="80px">
                            </c:when>
                            <c:otherwise>
                                No Image
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <button onclick="editCustomer(${sessionScope.customer.id})"
                                class="btn btn-primary w-25 mt-4 mb-5">Edit
                        </button>
                    </td>
                <tr>

                </tbody>
            </table>


            <c:choose>
                <c:when test="${sessionScope.selectedDate == null}">
                    <a hidden="hidden" class="btn btn-danger w-25 mt-5" href="../timing-select.jsp">Continue
                        booking</a>
                </c:when>

                <c:otherwise>
                    <a class="btn btn-danger w-25 mt-5" href="../timing-select.jsp">Continue booking</a>
                </c:otherwise>
            </c:choose>

        </div>
        <jsp:include page="/./footer.jsp"/>
    </div>

</div>

<script>
    function editCustomer(id) {
        window.location.replace("/customer.do?edit=" + id);
    }

</script>


<jsp:include page="/js-include.jsp"/>
</body>

</html>
