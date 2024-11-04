<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

    <jsp:include page="/admin/admin-sidebar.jsp"/>
    
    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>
        
        <div class="content flex-column justify-content-center  align-items-center flex-grow-1">

            <div class="d-flex p-4 w-100">

                <div class="p-5">
                    <i class="fa mb-3" style="font-size: xxx-large"></i>
                    <h1>Services</h1>
                </div>

                <div style="margin-left: 5%">
                    <form action="services.do" method="post" enctype="multipart/form-data">

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="name" placeholder="Name type to search" oninput="findServicesByName(this.value)">

                            <select name="servicesType" class="m-1">
                                <option value="HAIRSTYLE">Hairstyle</option>
                                <option value="MAKEUP">MakeUp</option>
                                <option value="SANITARY">Sanitary</option>
                                <option value="NAILS">Nail</option>
                            </select>

                            <select name="status" class="m-1">
                                <option value="true">active</option>
                                <option value="false">not active</option>
                            </select>

                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="stylistName" placeholder="StylistName">

                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1" type="date" name="releasedDate" placeholder="ReleasedDate">

                            <input type="file" name="image" class="m-1">

                        </div>

                        <div class="d-flex mb-4">
                            <input class="m-1 w-75" type="text" name="description" placeholder="Description">
                            <input class="btn btn-dark m-1 w-25" type="submit" value="Save">
                        </div>
                    </form>
                </div>
            </div>

            <div>
                <h4 class="mb-0 mt-3">All Services</h4>
            </div>


            <div class="d-flex justify-content-center p-4 w-100">

                <table id="allResultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>StylistName</th>
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
                            <td>${services.id}</td>
                            <td>${services.name}</td>
                            <td>${services.stylistName}</td>
                            <td>${services.servicesType}</td>
                            <td>${services.available}</td>
                            <td>${services.status}</td>
                            <td>${fn:substring(services.description, 0, 10)}...</td>
                            <td>
                                <div class="d-flex">
                                    <button onclick="editServices(${services.id})" class="btn btn-primary w-50">Edit</button>
                                    <button onclick="removeServices(${services.id})" class="btn btn-danger w-50">Remove</button>
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

    function editServices(id) {
        window.location.replace("/services.do?edit=" + id);
    }

    function removeServices(id){
        fetch("/rest/services/" + id, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    // Redirect if deletion was successful
                    window.location = "/services.do";
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

    function findServicesByName(name) {

        // AJAX call to fetch data from the API
        $.ajax({
            url: "/rest/services/findByName/" + name,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function(response) {
                // Clear previous results
                $("#allResultTable tbody").empty();


                if (typeof response === 'object' && response !== null) {
                    // Handle a single object response
                    var button = "<button class='btn btn-primary w-100' onclick='editServices(" + response.id + ")'>Edit</button>";
                    var button1 = "<button class='btn btn-danger w-100' onclick='removeServices(" + response.id + ")'>Remove</button>";
                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + response.stylistName + "</td>" +
                        "<td>" + response.releasedDate + "</td>" +
                        "<td>" + response.servicesType + "</td>" +
                        "<td>" + response.available + "</td>" +
                        "<td>" + response.status + "</td>" +
                        "<td>" + response.description.substring(0,10) + "</td>" +
                        "<td>" + button + "</td>" +
                        "<td>" + button1 + "</td>" +
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