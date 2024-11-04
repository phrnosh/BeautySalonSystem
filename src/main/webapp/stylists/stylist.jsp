<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Stylists</title>

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

    <jsp:include page="/managers/manager-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content d-flex flex-column align-items-center flex-grow-1">

            <div class="d-flex p-4 w-100">

                <div class="p-5">
<%--                    <i class="fa fa-vcard mb-3" style="font-size: xxx-large"></i>--%>
<%--                    <h1>Manager</h1>--%>
                        <a> <img class="fa col-12 col-md-9 col-lg-3" style="max-width: 100%; border-radius: 15%; margin:3%; font-size: xxx-large;" src="../assets/images/manager-btn1.jpg" > </a>
                </div>

                <div style="margin-left: 5%">
                    <form action="stylist.do" method="post" enctype="multipart/form-data">

                        <div class="input-register d-flex mb-2">

                            <input class="m-1" type="text" name="name" placeholder="Name">

                            <input class="m-1 text-danger-emphasis bg-secondary-subtle" type="text" name="family" placeholder="Family" oninput="findManagerByFamily(this.value)">

                        </div>

                        <div class="input-register d-flex mb-2">

                            <input class="m-1" type="text" name="username" placeholder="Username">

                            <input class="m-1" type="text" name="password" placeholder="Password">

                            <input class="m-1" type="text" name="email" placeholder="Email">

                        </div>

                        <div class="input-register d-flex mb-2">

                            <input class="m-1" type="text" name="career" placeholder="career">

                            <input class="m-1 text-danger-emphasis bg-secondary-subtle" type="text" name="nationalCode" placeholder="National Code - Search"  oninput="findManagerByNationalCode(this.value)">

                            <input class="m-1" type="text" name="phoneNumber" placeholder="Phone Number">

                            <input class="m-1" type="text" name="address" placeholder="Address">

                        </div>

                        <div class="d-flex mb-4">

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
                        <th>Career</th>
                        <th>National Code</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>Address</th>
                        <th>Image</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="stylist" items="${sessionScope.stylists}">

                        <tr>
                            <td>${stylist.id}</td>
                            <td>${stylist.name}</td>
                            <td>${stylist.family}</td>
                            <td>${stylist.user.username}</td>
                            <td>${stylist.user.password}</td>
                            <td>${stylist.career}</td>
                            <td>${stylist.nationalCode}</td>
                            <td>${stylist.phoneNumber}</td>
                            <td>${stylist.email}</td>
                            <td>${stylist.address}</td>

                            <td>

                                <c:choose>
                                    <c:when test="${stylist.imageUrl != ''}">
                                        <img src="${stylist.imageUrl}" alt="Stylist Image" height="80px" width="80px">
                                    </c:when>
                                    <c:otherwise>
                                        No Image
                                    </c:otherwise>
                                </c:choose>

                            </td>


                            <td>
                                <button onclick="editStylist(${stylist.id})" class="btn btn-primary w-25 mt-4">Edit</button>
                                <button onclick="removeStylist(${stylist.id})" class="btn btn-danger w-50 mt-4">Remove</button>
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


    function editStylist(id) {
        window.location.replace("/stylist.do?edit=" + id);
    }

    function removeStylist(id) {
        fetch("/rest/stylist/" + id, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    // Redirect if deletion was successful
                    window.location = "/stylist.do";
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


    function findStylistByNationalCode(nationalCode) {

        $.ajax({
            url: "/rest/stylist/findByNationalCode/" + nationalCode,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (typeof response === 'object' && response !== null) {
                    var imageHtml = response.imageUrl ? "<img src='" + response.imageUrl + "' alt='Stylist Image' height='80px' width='80px'>" : "No Image";


                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + response.family + "</td>" +
                        "<td>" + "" + "</td>" +
                        "<td>" + "" + "</td>" +
                        "<td>" + response.salonName + "</td>" +
                        "<td>" + response.nationalCode + "</td>" +
                        "<td>" + response.phoneNumber + "</td>" +
                        "<td>" + response.email + "</td>" +
                        "<td>" + response.address + "</td>" +
                        "<td>" + imageHtml + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editStylist(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeStylist(" + response.id + ")'>Remove</button>" + "</td>" +
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


    function findStylistByFamily(family) {

        $.ajax({
            url: "/rest/stylist/findByFamily/" + family,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (Array.isArray(response) && response.length > 0) {
                    response.forEach(function (stylist) {
                        var imageHtml = stylist.imageUrl ? "<img src='" + stylist.imageUrl + "' alt='Stylist Image' height='80px' width='80px'>" : "No Image";


                        var row = "<tr>" +
                            "<td>" + stylist.id + "</td>" +
                            "<td>" + stylist.name + "</td>" +
                            "<td>" + stylist.family + "</td>" +
                            "<td>" + "" + "</td>" +
                            "<td>" + "" + "</td>" +
                            "<td>" + stylist.salonName + "</td>" +
                            "<td>" + stylist.nationalCode + "</td>" +
                            "<td>" + stylist.phoneNumber + "</td>" +
                            "<td>" + stylist.email + "</td>" +
                            "<td>" + stylist.address + "</td>" +
                            "<td>" + imageHtml + "</td>" +
                            "<td>" + "<button class='btn btn-primary' onclick='editStylist(" + stylist.id + ")'>Edit</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-danger' onclick='removeStylist(" + stylist.id + ")'>Remove</button>" + "</td>" +
                            "</tr>";

                        $("#resultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {
                    var imageHtml = response.imageUrl ? "<img src='" + response.imageUrl + "' alt='Stylist Image' height='80px' width='80px'>" : "No Image";


                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + response.family + "</td>" +
                        "<td>" + "" + "</td>" +
                        "<td>" + "" + "</td>" +
                        "<td>" + response.salonName + "</td>" +
                        "<td>" + response.nationalCode + "</td>" +
                        "<td>" + response.phoneNumber + "</td>" +
                        "<td>" + response.email + "</td>" +
                        "<td>" + response.address + "</td>" +
                        "<td>" + imageHtml + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editStylist(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeStylist(" + response.id + ")'>Remove</button>" + "</td>" +
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