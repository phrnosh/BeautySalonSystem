<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cinema</title>

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

    <jsp:include page="/admin/admin-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>

        <div class="content flex-column justify-content-center  align-items-center flex-grow-1">

            <div class="d-flex p-4 w-100">
                <div class="p-4 d-flex w-100 align-items-center justify-content-between">
                    <div>
                        <i class="fa  mb-3" style="font-size: xxx-large"></i>
                        <h1>Services</h1>
                    </div>
                    <div class="ml-auto d-flex flex-column align-items-center">
                        <h4>Services Search</h4>
                        <label style="font-size: large">The Name Of Services</label>
                        <input type="text" name="name" oninput="findServicesByName(this.value)">
                    </div>
                </div>
            </div>

            <div>
                <h4 class="mb-0">${sessionScope.manager.salonName} Salon Services</h4>
            </div>

            <div class="d-flex justify-content-center p-4 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th hidden="hidden">ID</th>
                        <th>Name</th>
                        <th>Stylist Name</th>
                        <th>Released Date</th>
                        <th>Type</th>
                        <th>Available</th>
                        <th>Status</th>
                        <th>Description</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="services" items="${sessionScope.servicess}">

                        <tr>
                            <td hidden="hidden">${services.id}</td>
                            <td>${services.name}</td>
                            <td>${services.stylistName}</td>
                            <td>${services.releasedDate}</td>
                            <td>${services.servicesType}</td>
                            <td>${services.available}</td>
                            <td>${services.status}</td>
                            <td>${fn:substring(services.description, 0, 10)}...</td>
                            <td>
                                <div class="d-flex">
                                    <button onclick="removeServicesFromList(${services.id})" class="btn btn-danger w-100">Remove</button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div>
                <h4 class="mb-0 mt-3">All Services</h4>
            </div>

            <div class="d-flex justify-content-center p-4 w-100">
                <table id="allResultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th hidden="hidden">ID</th>
                        <th>Name</th>
                        <th>Stylist Name</th>
                        <th>Released Date</th>
                        <th>Type</th>
                        <th>Available</th>
                        <th>Status</th>
                        <th>Description</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="services" items="${sessionScope.allServices}">

                        <tr>
                            <td hidden="hidden">${services.id}</td>
                            <td>${services.name}</td>
                            <td>${services.stylistName}</td>
                            <td>${services.releasedDate}</td>
                            <td>${services.servicesType}</td>
                            <td>${services.available}</td>
                            <td>${services.status}</td>
                            <td>${fn:substring(services.description, 0, 10)}...</td>
                            <td>
                                <div class="d-flex">
                                    <button onclick="addServices(${services.id})" class="btn btn-dark w-100">Add</button>
                                </div>
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

    function addServices(id) {
        window.location.replace("/services.do?add=" + id);
    }

    function removeServicesFromList(id){
        window.location.replace("/services.do?removeFromList=" + id);
    }

    // Function to call the API and display the result in a table
    function findServicesByName(name) {

        // AJAX call to fetch data from the API
        $.ajax({
            url: "/rest/services/findByName/" + name,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function(response) {
                // Clear previous results
                $("#allResultTable tbody").empty();

                // Check if response is an array
                if (Array.isArray(response) && response.length > 0) {
                    // Loop through the array and create table rows
                    response.forEach(function(services) {
                        var button = "<button class='btn btn-dark' onclick='addServices(" + services.id + ")'>Add</button>";
                        var row = "<tr>" +
                            "<td hidden='hidden'>" + services.id + "</td>" +
                            "<td>" + services.name + "</td>" +
                            "<td>" + services.stylistName + "</td>" +
                            "<td>" + services.releasedDate + "</td>" +
                            "<td>" + services.servicesType + "</td>" +
                            "<td>" + services.available + "</td>" +
                            "<td>" + services.status + "</td>" +
                            "<td>" + services.description.substring(0,10) + "</td>" +
                            "<td>" + button + "</td>" +
                            "</tr>";

                        $("#allResultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {
                    // Handle a single object response
                    var button = "<button class='btn btn-dark w-100' onclick='addServices(" + response.id + ")'>Add</button>";
                    var row = "<tr>" +
                        "<td hidden='hidden'>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + services.stylistName + "</td>" +
                        "<td>" + services.releasedDate + "</td>" +
                        "<td>" + services.servicesType + "</td>" +
                        "<td>" + services.available + "</td>" +
                        "<td>" + response.status + "</td>" +
                        "<td>" + response.description.substring(0,10) + "</td>" +
                        "<td>" + button + "</td>" +
                        "</tr>";
                    $("#allResultTable tbody").append(row);
                } else {
                    // If no data, services "No records found" message
                    var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
                    $("#allResultTable tbody").append(noDataRow);
                }
            },

            error: function(xhr, status, error) {
                // alert("Error fetching data: " + error);
            }
        });
    }

</script>


<jsp:include page="../js-include.jsp"/>

</body>
</html>