<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users</title>

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

    <jsp:include page="/admin/admin-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content d-flex flex-column align-items-center flex-grow-1">

            <div class="d-flex p-4 w-100">

                <div class="p-5">
                    <i class="fa fa-user mb-3" style="font-size: xxx-large"></i>
                    <h1>User</h1>
                </div>

                <div class=" w-50 align-content-center" style="margin-left: 5%">

                    <input class="input-group m-2 text-danger-emphasis bg-secondary-subtle" type="text" name="username" placeholder="Enter username to search"
                           oninput="findUserByUsername(this.value)">


                    <label for="role">Select role to search</label>
                    <select name="role" class="form-control" id="role" oninput="findUserByRole(this.value)">
                        <option value="admin">Admin</option>
                        <option value="manager">Manager</option>
                        <option value="customer">Customer</option>
                    </select>



                </div>


            </div>

            <div>
                <h4 class="mb-0">My Account</h4>
            </div>

            <div class="d-flex justify-content-center p-5 w-100 mb-3">

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


            <div>
                <h4 class="mb-0">All Users</h4>
            </div>


            <div class="d-flex justify-content-center p-5 w-100">

                <table id="allResultTable" border="1" class="table-light w-100">
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

                    <c:forEach var="user" items="${sessionScope.allUsers}">

                        <tr>
                            <td>${user.username}</td>
                            <td>${user.password}</td>
                            <td>${user.role.role}</td>
                            <td>${user.locked}</td>

                            <td>
                                <button onclick="editUser('${user.username}')" class="btn btn-primary w-25 mt-4">Edit
                                </button>
                            </td>
                        </tr>

                    </c:forEach>

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

    // function editUser(username) {
    //     window.location.replace("/users.do?edit=" + username);
    // }


    function findUserByUsername(username) {

        $.ajax({
            url: "/rest/user/findUserByUsername/" + username,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#allResultTable tbody").empty();

                if (typeof response === 'object' && response !== null) {


                    var row = "<tr>" +
                        "<td>" + response.username + "</td>" +
                        "<td>" + response.password + "</td>" +
                        "<td>" + "" + "</td>" +
                        "<td>" + response.locked + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editUser(\"" + response.username + "\")'>Edit</button>" + "</td>"
                    "</tr>";
                    $("#allResultTable tbody").append(row);
                } else {
                    var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
                    $("#allResultTable tbody").append(noDataRow);
                }
            },
            error: function (xhr, status, error) {
                // alert("Error fetching data: " + error);
            }
        });
    }



    function findUserByRole(role) {

        $.ajax({
            url: "/rest/user/findByRole/" + role,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#allResultTable tbody").empty();

                if (Array.isArray(response) && response.length > 0) {
                    response.forEach(function (user) {

                        var row = "<tr>" +
                            "<td>" + user.username + "</td>" +
                            "<td>" + user.password + "</td>" +
                            "<td>" + role + "</td>" +
                            "<td>" + user.locked + "</td>" +
                            "<td>" + "<button class='btn btn-primary' onclick='editUser(\"" + user.username + "\")'>Edit</button>" + "</td>"
                        "</tr>";

                        $("#allResultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {

                    var row = "<tr>" +
                        "<td>" + response.username + "</td>" +
                        "<td>" + response.password + "</td>" +
                        "<td>" + role + "</td>" +
                        "<td>" + response.locked + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editUser(\"" + user.username + "\")'>Edit</button>" + "</td>"
                    "</tr>";
                    $("#allResultTable tbody").append(row);
                } else {
                    var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
                    $("#allResultTable tbody").append(noDataRow);
                }
            },
            error: function (xhr, status, error) {
                // alert("Error fetching data: " + error);
            }
        });
    }


</script>


<jsp:include page="../js-include.jsp"/>

</body>
</html>