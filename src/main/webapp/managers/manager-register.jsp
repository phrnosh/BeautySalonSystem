<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<form action="manager.do" method="post">
    <input type="text" name="name">
    <input type="text" name="family">
    <input type="submit" value="save">
</form>

<c:if test="${not empty sessionScope.managerList}">
    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Family</th>
            <th>Operation</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="manager" items="${sessionScope.managerList}">
            <tr>
                <td>${manager.id}</td>
                <td>${manager.name}</td>
                <td>${manager.family}</td>
                <td>
                    <button onclick="editManager(${manager.id})">Edit</button>
<%--                    <button onclick="Remove(${person.id})">Remove</button>--%>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>


<c:if test="${empty sessionScope.managerList}">
    <h1>No Content</h1>
</c:if>

</body>
</html>
