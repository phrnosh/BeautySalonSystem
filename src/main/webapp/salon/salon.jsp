<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Salon</title>

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


        <div class="content d-flex flex-column  align-items-center flex-grow-1">


            <div class="d-flex p-4 w-100">

                <div class="p-5">
                    <i class="fa mb-3" style="font-size: xxx-large"></i>
                    <h1>Salon</h1>
                </div>

                <div style="margin-left: 5%">
                    <form action="salon.do" method="post" enctype="multipart/form-data">

                        <input type="hidden" name="managerId" value="">

                        <div class="d-flex mb-4">

                            <input class="m-1 text-danger-emphasis bg-secondary-subtle" type="text" name="name" placeholder="Name type to search"
                                   oninput="findSalonByName(this.value)">

                            <select name="status" class="m-1">
                                <option value="true">active</option>
                                <option value="false">not active</option>
                            </select>

                            <button type="button" onclick="setManager()" class="btn btn-secondary w-50 m-1">Select
                                Manager
                            </button>


                        </div>


                        <div class="d-flex mb-4">

                            <input class="m-1 w-75" type="text" name="description" placeholder="Description">

                            <input type="file" name="image" class="m-1">

                        </div>

                        <div class="d-flex mb-4">
                            <input class="m-1 w-75" type="text" name="address" placeholder="Address">
                            <input class="btn btn-dark m-1 w-25" type="submit" value="Save">
                        </div>

                    </form>

                </div>

                <div id="manager-select-div" style="margin-left: 5%; display: none">

                    <h5>
                        Manager
                    </h5>

                    <table id="managerTable" border="1" class="table-light w-100">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Family</th>
                            <th>National Code</th>
                            <th>Operation</th>
                        </tr>
                        </thead>

                        <tbody>


                        </tbody>
                    </table>

                </div>


            </div>


            <div class="d-flex justify-content-center p-5 w-100">

                <div class="w-100">
                    <h3>
                        All Salons
                    </h3>

                    <table id="resultTable" border="1" class="table-light w-100">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Status</th>
                            <th>Description</th>
                            <th>Address</th>
                            <th>Image</th>
                            <th>Operation</th>
                        </tr>
                        </thead>

                        <tbody>

                        <c:forEach var="salon" items="${sessionScope.allSalons}">
                            <tr>
                                <td>${salon.id}</td>
                                <td>${salon.name}</td>
                                <td>${salon.status}</td>
                                <td>${salon.description}</td>
                                <td>${salon.address}</td>

                                <td>

                                    <c:choose>
                                        <c:when test="${salon.imageUrl != ''}">
                                            <img src="${salon.imageUrl}" alt="Salon Image" height="80px" width="80px">
                                        </c:when>
                                        <c:otherwise>
                                            No Image
                                        </c:otherwise>
                                    </c:choose>

                                </td>


                                <td>
                                    <button onclick="setServices(${salon.id})" class="btn btn-secondary w-auto mt-4">Services
                                    </button>

                                    <button onclick="setTiming(${salon.id})" class="btn btn-secondary w-auto mt-4">
                                        Timing
                                    </button>
                                    <button onclick="editSalon(${salon.id})" class="btn btn-primary w-auto mt-4">
                                        Edit
                                    </button>
                                    <button onclick="removeSalon(${salon.id})" class="btn btn-danger w-auto mt-4">
                                        Remove
                                    </button>
                                </td>

                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <jsp:include page="/footer.jsp"/>
    </div>
</div>

<script>
    function editSalon(id) {
        window.location.replace("/salon.do?edit=" + id);
    }

    function removeSalon(id) {
        fetch("/rest/salon/" + id, {
            method: "DELETE"
        }).then(() => {
            window.location = "/salon.do"
        })
    }

    function setManager() {

        $("#manager-select-div").show();

        $.ajax({
            url: "/rest/manager/manager-for-salon",
            method: "GET",
            dataType: "json",
            success: function (response) {
                $("#managerTable tbody").empty();

                if (response && response.length > 0) {
                    response.forEach(function (manager) {
                        var row = "<tr>" +
                            "<td>" + manager.id + "</td>" +
                            "<td>" + manager.name + "</td>" +
                            "<td>" + manager.family + "</td>" +
                            "<td>" + manager.nationalCode + "</td>" +
                            "<td><button onclick='selectManager(" + manager.id + ")' class='btn btn-secondary'>Select</button></td>" +
                            "</tr>";
                        $("#managerTable tbody").append(row);
                    });
                } else {
                    var noDataRow = "<tr><td colspan='5'>No records found</td></tr>";
                    $("#managerTable tbody").append(noDataRow);
                }
            },
            error: function (xhr, status, error) {
                alert("Error fetching data: " + error);
            }
        });
    }

    // Function to set selected manager in a hidden field
    function selectManager(managerId) {
        $("input[name='managerId']").val(managerId);
        alert("Manager with ID " + managerId + " selected!");
    }

    // Form handling for salon creation including manager selection
    $('form').submit(function () {
        var managerId = $("input[name='managerId']").val();
        if (!managerId) {
            alert("Please select a manager before submitting.");
            return false; // Prevent form submission if no manager selected
        }
    });


    function setServices(id) {
        window.location.replace("/services.do?salonId=" + id);
    }

    function setTiming(id) {
        window.location.replace("/timing.do?salonId=" + id);
    }


    function findSalonByName(name) {

        $.ajax({
            url: "/rest/salon/findByName/" + name,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (Array.isArray(response) && response.length > 0) {
                    response.forEach(function (salon) {
                        var imageHtml = salon.imageUrl ? "<img src='" + salon.imageUrl + "' alt='Salon Image' height='80px' width='80px'>" : "No Image";

                        var row = "<tr>" +
                            "<td>" + salon.id + "</td>" +
                            "<td>" + salon.name + "</td>" +
                            "<td>" + salon.status + "</td>" +
                            "<td>" + salon.description + "</td>" +
                            "<td>" + salon.address + "</td>" +
                            "<td>" + imageHtml + "</td>" +
                            "<td>" + "<button class='btn btn-secondary' onclick='setServices(" + salon.id + ")'>Services</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-secondary' onclick='setTiming(" + salon.id + ")'>Timing</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-primary' onclick='editSalon(" + salon.id + ")'>Edit</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-danger' onclick='removeSalon(" + salon.id + ")'>Remove</button>" + "</td>" +
                            "</tr>";

                        $("#resultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {
                    var imageHtml = response.imageUrl ? "<img src='" + response.imageUrl + "' alt='Salon Image' height='80px' width='80px'>" : "No Image";

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + response.status + "</td>" +
                        "<td>" + response.description + "</td>" +
                        "<td>" + response.address + "</td>" +
                        "<td>" + imageHtml + "</td>" +
                        "<td>" + "<button class='btn btn-secondary' onclick='setServices(" + response.id + ")'>Services</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-secondary' onclick='setTiming(" + response.id + ")'>Timing</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editSalon(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeSalon(" + response.id + ")'>Remove</button>" + "</td>" +
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


<jsp:include page="../js-include.jsp"/>

</body>
</html>