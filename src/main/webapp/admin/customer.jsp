<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Customers</title>

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

            <div class="d-flex p-4 w-100 ">

                <div class="p-5 ">
                    <i class="fa fa-person mb-3" style="font-size: xxx-large"></i>
                    <h1>Customer</h1>
                </div>

                <div class=" w-50 align-content-center" style="margin-left: 5%">

                    <input class="input-group m-2" type="number" name="customerId" placeholder="Customer ID type to search"
                           oninput="findCustomerById(this.value)">

                    <input class="input-group m-2" type="text" name="family" placeholder="Customer Family type to search"
                           oninput="findCustomerByFamily(this.value)">

                    <input class="input-group m-2" type="text" name="phoneNumber" placeholder="Phone Number type to search"
                           oninput="findCustomerByPhoneNumber(this.value)">

                </div>


            </div>


            <div>
                <h4 class="mb-0">All Customers</h4>
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
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="customer" items="${sessionScope.allCustomers}">

                        <tr>
                            <td>${customer.id}</td>
                            <td>${customer.name}</td>
                            <td>${customer.family}</td>
                            <td>${customer.username}</td>
                            <td>${customer.password}</td>
                            <td>${customer.phoneNumber}</td>
                            <td>${customer.email}</td>


                            <td>
                                <button onclick="editCustomer(${customer.id})" class="btn btn-primary w-25 mt-4">Edit</button>
                                <button onclick="removeCustomer(${customer.id})" class="btn btn-danger w-50 mt-4">Remove</button>
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


    function editCustomer(id) {
        window.location.replace("/customer.do?edit=" + id);
    }

    function removeCustomer(id) {
        fetch("/rest/customer/" + id, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    // Redirect if deletion was successful
                    window.location = "/customer.do";
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

    function findCustomerById(id) {

        $.ajax({
            url: "/rest/customer/findById/" + id,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (typeof response === 'object' && response !== null) {

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + response.family + "</td>" +
                        "<td>" + response.username + "</td>" +
                        "<td>" + response.password + "</td>" +
                        "<td>" + response.phoneNumber + "</td>" +
                        "<td>" + response.email + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editCustomer(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeCustomer(" + response.id + ")'>Remove</button>" + "</td>" +
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


    function findCustomerByFamily(family) {

        $.ajax({
            url: "/rest/customer/findByFamily/" + family,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (Array.isArray(response) && response.length > 0) {
                    response.forEach(function (customer) {

                        var row = "<tr>" +
                            "<td>" + customer.id + "</td>" +
                            "<td>" + customer.name + "</td>" +
                            "<td>" + customer.family + "</td>" +
                            "<td>" + customer.username + "</td>" +
                            "<td>" + customer.password + "</td>" +
                            "<td>" + customer.phoneNumber + "</td>" +
                            "<td>" + customer.email + "</td>" +
                            "<td>" + "<button class='btn btn-primary' onclick='editCustomer(" + customer.id + ")'>Edit</button>" + "</td>" +
                            "<td>" + "<button class='btn btn-danger' onclick='removeCustomer(" + customer.id + ")'>Remove</button>" + "</td>" +
                            "</tr>";

                        $("#resultTable tbody").append(row);
                    });
                } else if (typeof response === 'object' && response !== null) {


                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + response.family + "</td>" +
                        "<td>" + response.username + "</td>" +
                        "<td>" + response.password + "</td>" +
                        "<td>" + response.phoneNumber + "</td>" +
                        "<td>" + response.email + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editCustomer(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeCustomer(" + response.id + ")'>Remove</button>" + "</td>" +
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


    function findCustomerByPhoneNumber(phoneNumber) {

        $.ajax({
            url: "/rest/customer/findByPhoneNumber/" + phoneNumber,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function (response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                if (typeof response === 'object' && response !== null) {

                    var row = "<tr>" +
                        "<td>" + response.id + "</td>" +
                        "<td>" + response.name + "</td>" +
                        "<td>" + response.family + "</td>" +
                        "<td>" + response.username + "</td>" +
                        "<td>" + response.password + "</td>" +
                        "<td>" + response.phoneNumber + "</td>" +
                        "<td>" + response.email + "</td>" +
                        "<td>" + "<button class='btn btn-primary' onclick='editCustomer(" + response.id + ")'>Edit</button>" + "</td>" +
                        "<td>" + "<button class='btn btn-danger' onclick='removeCustomer(" + response.id + ")'>Remove</button>" + "</td>" +
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