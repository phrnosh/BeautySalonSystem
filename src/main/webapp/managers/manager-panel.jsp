<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager</title>


</head>
<body>

<div style="margin-top: 100px">

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>StylistName</th>
            <th>dateOfModified</th>
            <th>Services Type</th>
            <th>Available</th>
            <th>Status</th>
            <th>Description</th>
            <th>Operation</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="services" items="${sessionScope.services}">
            <tr>
                <td>${services.id}</td>
                <td>${services.name}</td>
                <td>${services.stylistName}</td>
                <td>${services.dateOfModified}</td>
                <td>${services.servicesType}</td>
                <td>${services.available}</td>
                <td>${services.status}</td>
                <td>${services.description}</td>

            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>

<div style="margin-top: 100px">

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Services</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Status</th>
            <th>Description</th>
            <th>Operation</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="timing" items="${sessionScope.timing}">
            <tr>
                <td>${timing.id}</td>
                <td>${timing.services.name}</td>
                <td>${timing.startTime}</td>
                <td>${timing.endTime}</td>
                <td>${timing.status}</td>
                <td>${timing.description}</td>

            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>

</body>
</html>