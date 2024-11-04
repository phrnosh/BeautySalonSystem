<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Salon</title>

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

    <jsp:include page="/managers/manager-sidebar.jsp"/>


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

                            <input class="m-1 text-danger-emphasis bg-secondary-subtle" type="text" name="name" placeholder="Name type to search" oninput="findServicesByName(this.value)">


                            <select name="servicesType" class="m-1">
                                <option value="HAIRSTYLE">HairStyle</option>
                                <option value="MAKEUP">Makeup</option>
                                <option value="SANITARY">Sanitary</option>
                                <option value="NAILS">Nails</option>
                            </select>

                            <div class="d-flex mb-4">

                                <select name="stylistId" class="m-1">
                                    <c:forEach var="stylist" items="${sessionScope.stylists}">

                                        <option value="${stylist.id}">${stylist.name} ${stylist.family}</option>

                                    </c:forEach>
                                </select>

<%--                                <input class="m-1" type="text" name="stylistName" placeholder="stylistName">--%>

                            </div>

                            <select name="status" class="m-1">
                                <option value="true">active</option>
                                <option value="false">not active</option>
                            </select>

                        </div>


                        <div class="d-flex mb-4">

<%--                            <input class="m-1" type="date" name="dateOfModified" placeholder="dateOfModified">--%>

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
                <h4 class="mb-0">Your Services</h4>
            </div>

            <div class="d-flex justify-content-center p-4 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th hidden="hidden">ID</th>
                        <th>Name</th>
                        <th>StylistName</th>
<%--                        <th>dateOfModified</th>--%>
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
                            <td hidden="hidden">${services.id}</td>
                            <td>${services.name}</td>
                            <td>${services.stylistName}</td>
<%--                            <td>${services.dateOfModified}</td>--%>
                            <td>${services.servicesType}</td>
                            <td>${services.available}</td>
                            <td>${services.status}</td>
                            <td>${fn:substring(services.description, 0, 10)}...</td>
                            <td>
                                <div class="d-flex">
                                    <button onclick="editServices(${services.id})" class="btn btn-primary w-auto mt-4">Edit</button>
                                    <button onclick="removeServices(${services.id})" class="btn btn-danger w-auto mt-4">Remove</button>                                </div>

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

    function removeServices(id) {
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

</script>
<jsp:include page="../js-include.jsp"/>

</body>
</html>