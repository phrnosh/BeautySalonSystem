<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
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


<jsp:include page="/./short-user-info.jsp"/>
<jsp:include page="/./navbar.jsp"/>
<br>
<!-- image countent -->
<div id="image-count" style="height: 68em;">
    <img src="/assets/images/mainpic.jpg" style="width:100%" alt="">
    <div class="container">
        <div class="caption-site" >
            <div class="container top-header">
                <div class="search-box d-lg-flex">
                    <div class="input-group md-form form-sm form-1 pl-0">
                        <table id="resultTable" border="1" class="table-light w-100">
                            <thead>
                            <tr>
                                <th hidden="hidden">ID</th>
                                <th>Name</th>
                                <th>Family</th>
                                <th>Phone Number</th>
                                <th>Email</th>
                                <th>Image</th>
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
                            <tr>

                            </tbody>
                        </table>
                        <input class="form-control mr-sm-2" type="search" placeholder="Search Salon..." aria-label="Search">
                        <div class="input-group-prepend" style="padding-bottom: 2px;" >
                    <span style="background-color: black; border-radius: 8px;" class="input-group-text purple lighten-3"
                          id="basic-text1"><svg style="color: #fff;" width="1em" height="1em" viewBox="0 0 16 16"
                                                class="bi bi-search" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd"
                                  d="M10.442 10.442a1 1 0 0 1 1.415 0l3.85 3.85a1 1 0 0 1-1.414 1.415l-3.85-3.85a1 1 0 0 1 0-1.415z" />
                            <path fill-rule="evenodd"
                                  d="M6.5 12a5.5 5.5 0 1 0 0-11 5.5 5.5 0 0 0 0 11zM13 6.5a6.5 6.5 0 1 1-13 0 6.5 6.5 0 0 1 13 0z" />
                        </svg></span>
                        </div>
                    </div>
                </div>
                <h1 class="d-lg-block" style="text-align: center; font-weight:bolder; font-size:6em;">Beauty Salon</h1>
                <%--             <p style="text-align: justify;"></p>--%>
                <%--            <img src="/assets/images/mainpic.jpg" class="caption-back d-none d-lg-flex" alt="">--%>
                <button onclick="editCustomer(${sessionScope.customer.id})" class="btn btn-primary w-25 mt-4 mb-5">Edit</button>

                <c:choose>
                    <c:when test="${sessionScope.selectedTiming == null}">
                        <a hidden="hidden" class="btn btn-danger w-25 mt-5" href="services.do">Continue booking</a>
                    </c:when>

                    <c:otherwise>
                        <a class="btn btn-danger w-25 mt-5" href="services.do">Continue booking</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/./footer.jsp"/>

<script>
    function editCustomer(id) {
        window.location.replace("/customer.do?edit=" + id);
    }


    // Function to call the API and display the result in a table
    function findSalonByName() {
        var name = document.getElementById("salonName").value;

        // AJAX call to fetch data from the API
        $.ajax({
            // url: "http://localhost:8080/yourapp/manager/findByName/" + name,
            method: "GET",
            dataType: "json", // Expect JSON response
            success: function(response) {
                // Clear previous results
                $("#resultTable tbody").empty();

                // Check if there is any data in the response
                if (response && response.length > 0) {
                    // Loop through the response and create table rows
                    response.forEach(function(salon) {
                        var row = "<tr>" +
                            "<td>" + salon.id + "</td>" +
                            "<td>" + salon.name + "</td>" +
                            "</tr>";
                        $("#resultTable tbody").append(row); // Add row to the table
                    });
                } else {
                    // If no data, show "No records found" message
                    var noDataRow = "<tr><td colspan='3'>No records found</td></tr>";
                    $("#resultTable tbody").append(noDataRow);
                }
            },
            error: function(xhr, status, error) {
                // Handle error case
                alert("Error fetching data: " + error);
            }
        });
    }
</script>
</body>
<jsp:include page="/js-include.jsp"/>

</html>
