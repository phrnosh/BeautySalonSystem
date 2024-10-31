<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Managers</title>

    <jsp:include page="../css-include.jsp"/>

</head>
<body style="background-color:rgba(202, 194, 188);">

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
                    <i class="fa fa-vcard mb-3" style="font-size: xxx-large"></i>
                    <h1>Manager</h1>
                </div>

                <div style="margin-left: 5%">
                    <form action="manager.do" method="post" enctype="multipart/form-data">

                        <div class="d-flex mb-4">



                            <input class="m-1" type="text" name="name" placeholder="Name">

                            <input class="m-1 text-danger-emphasis bg-secondary-subtle" type="text" name="family" placeholder="Family" oninput="findManagerByFamily(this.value)">

                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="username" placeholder="Username">

                            <input class="m-1" type="text" name="password" placeholder="Password">

                            <input class="m-1" type="text" name="email" placeholder="Email">

                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1 text-danger-emphasis bg-secondary-subtle" type="text" name="nationalCode" placeholder="National Code - Search"  oninput="findManagerByNationalCode(this.value)">

                            <input class="m-1" type="text" name="phoneNumber" placeholder="Phone Number">

                            <input class="m-1" type="text" name="address" placeholder="Address">

                            <input type="file" name="image" class="m-1">


                        </div>


                        <div class="d-flex mb-4">
                            <input class="btn btn-dark m-1 w-25" type="submit" value="Save">
                        </div>

                    </form>

                </div>


            </div>


            <div>
                <h4 class="mb-0">All Managers</h4>
            </div>


            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Family</th>
                        <th>Username</th>
                        <th>Password</th>
                        <th>Salon</th>
                        <th>National Code</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>Address</th>
                        <th>Image</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="manager" items="${sessionScope.allManagers}">

                        <tr>
                            <td>${manager.id}</td>
                            <td>${manager.name}</td>
                            <td>${manager.family}</td>
                            <td>${manager.user.username}</td>
                            <td>${manager.user.password}</td>
                            <td>${manager.salonId}</td>
                            <td>${manager.nationalCode}</td>
                            <td>${manager.phoneNumber}</td>
                            <td>${manager.email}</td>
                            <td>${manager.address}</td>

                            <td>

                                <c:choose>
                                    <c:when test="${manager.imageUrl != ''}">
                                        <img src="${manager.imageUrl}" alt="Manager Image" height="80px" width="80px">
                                    </c:when>
                                    <c:otherwise>
                                        No Image
                                    </c:otherwise>
                                </c:choose>

                            </td>


                            <td>
                                <button onclick="editManager(${manager.id})" class="btn btn-primary w-25 mt-4">Edit</button>
                                <button onclick="removeManager(${manager.id})" class="btn btn-danger w-50 mt-4">Remove</button>
                            </td>
                        </tr>

                    </c:forEach>

                    </tbody>

                </table>

            </div>


        </div>

    </div>


</div>


<script>


    function editManager(id) {
        window.location.replace("/manager.do?edit=" + id);
    }

    function removeManager(id) {
        fetch("/rest/manager/" + id, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    // Redirect if deletion was successful
                    window.location = "/manager.do";
                } else {
                    // If the response is not successful, read the error message
                    return response.text().then(errorMessage => {
                        // Alert the error message returned from the API
                        alert(errorMessage);
                    });
                }
            })
            .catch(error => {
                // Handle any other errors that may occur
                console.error('Error:', error);
                alert("An error occurred: " + error.message);
            });
    }


    function findManagerByNationalCode(nationalCode) {

        $.ajax({
            url: "/rest/manager/findByNationalCode/" + nationalCode,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (typeof response === 'object' && response !== null) {
                    var imageHtml = response.imageUrl ? "<img src='" + response.imageUrl + "' alt='Manager Image' height='80px' width='80px'>" : "No Image";


                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + response.family + "</td>" +
                        "<td>" + "" + "</td>" +
                        "<td>" + "" + "</td>" +
                        "<td>" + response.salonId + "</td>" +
                        "<td>" + response.nationalCode + "</td>" +
                        "<td>" + response.phoneNumber + "</td>" +
                        "<td>" + response.email + "</td>" +
                        "<td>" + response.address + "</td>" +
                        "<td>" + imageHtml + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editManager(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeManager(" + response.id + ")'>Remove</button>" + "</td>" +
                        "</tr>";
                    $("#resultTable tbody").append(row);
                } else {
                    var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
                    $("#resultTable tbody").append(noDataRow);
                }
            },
            error: function (xhr, status, error) {
                // alert("Error fetching data: " + error);
            }
        });
    }


    function findManagerByFamily(family) {

        $.ajax({
            url: "/rest/manager/findByFamily/" + family,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (Array.isArray(response) && response.length > 0) {
                    response.forEach(function (manager) {
                        var imageHtml = manager.imageUrl ? "<img src='" + manager.imageUrl + "' alt='Manager Image' height='80px' width='80px'>" : "No Image";


                        var row = "<tr>" +
                            "<td>" + manager.id + "</td>" +
                            "<td>" + manager.name + "</td>" +
                            "<td>" + manager.family + "</td>" +
                            "<td>" + "" + "</td>" +
                            "<td>" + "" + "</td>" +
                            "<td>" + manager.salonName + "</td>" +
                            "<td>" + manager.nationalCode + "</td>" +
                            "<td>" + manager.phoneNumber + "</td>" +
                            "<td>" + manager.email + "</td>" +
                            "<td>" + manager.address + "</td>" +
                            "<td>" + imageHtml + "</td>" +
                            "<td>" + "<button class='btn btn-primary' onclick='editManager(" + manager.id + ")'>Edit</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-danger' onclick='removeManager(" + manager.id + ")'>Remove</button>" + "</td>" +
                            "</tr>";

                        $("#resultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {
                    var imageHtml = response.imageUrl ? "<img src='" + response.imageUrl + "' alt='Customer Image' height='80px' width='80px'>" : "No Image";


                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + response.family + "</td>" +
                        "<td>" + "" + "</td>" +
                        "<td>" + "" + "</td>" +
                        "<td>" + response.salonId + "</td>" +
                        "<td>" + response.nationalCode + "</td>" +
                        "<td>" + response.phoneNumber + "</td>" +
                        "<td>" + response.email + "</td>" +
                        "<td>" + response.address + "</td>" +
                        "<td>" + imageHtml + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editManager(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeManager(" + response.id + ")'>Remove</button>" + "</td>" +
                        "</tr>";
                    $("#resultTable tbody").append(row);
                } else {
                    var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
                    $("#resultTable tbody").append(noDataRow);
                }
            },
            error: function (xhr, status, error) {
                // alert("Error fetching data: " + error);
            }
        });
    }


</script>

<jsp:include page="/footer.jsp"/>

<jsp:include page="../js-include.jsp"/>

</body>
</html>