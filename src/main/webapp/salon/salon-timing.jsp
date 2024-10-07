<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Timing</title>
    
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
                    <h1>Timing</h1>
                </div>
                
                <div style="margin-left: 5%">
                    <form action="timing.do" method="post">

                        <div class="d-flex mb-4">

                            <!-- Services Input -->
                            <div class="form-group col-md-8 m-1">
                                <label>Services</label>
                                <select name="services" id="services" class="form-control">
                                    <c:forEach var="services" items="${sessionScope.allUsableServices}">
                                        <option>${services.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                        </div>

                        <!-- Start and End Time Section -->
                        <div class="d-flex mb-4">

                            <div class="form-group col-md-6 m-1">
                                <label for="startTime">Start Time</label>
                                <input class="form-control" type="date" id="date" name="date">
                            </div>

                            <div class="form-group col-md-6 m-1">
                                <label for="startTime">Start Time</label>
                                <input class="form-control" type="time" id="startTime" name="startTime">
                            </div>

                            <div class="form-group col-md-6 m-1">
                                <label for="endTime">End Time</label>
                                <input class="form-control" type="time" id="endTime" name="endTime">
                            </div>

                        </div>

                        <!-- Status, Description, and Save Button Section -->
                        <div class="d-flex mb-4">
                            <!-- Status -->
                            <div class="form-group col-md-3 m-1">
                                <label for="status">Status</label>
                                <select name="status" class="form-control" id="status">
                                    <option value="true">active</option>
                                    <option value="false">not active</option>
                                </select>
                            </div>

                            <!-- Description -->
                            <div class="form-group col-md-6 m-1">
                                <label for="description">Description</label>
                                <input class="form-control" type="text" id="description" name="description" placeholder="Description">
                            </div>

                            <!-- Save Button -->
                            <div class="form-group col-md-3 d-flex align-items-end m-1">
                                <input class="btn btn-dark w-100" type="submit" value="Save">
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <div>
                <h4 class="mb-0">${sessionScope.salon.name} Salon Timing</h4>
            </div>

            <div class="d-flex justify-content-center p-4 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Services</th>
<%--                        <th>Remaining Capacity</th>--%>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Status</th>
                        <th>Description</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="timing" items="${sessionScope.salonTiming}">

                        <tr>
                            <td>${timing.id}</td>
                            <td>${timing.services.name}</td>
<%--                            <td>${timing.remainingCapacity}</td>--%>
                            <td>${timing.startTime}</td>
                            <td>${timing.endTime}</td>
                            <td>${timing.status}</td>
                            <td>${fn:substring(timing.description, 0, 10)}...</td>

                            <td>
                                <button onclick="editTiming(${timing.id})" class="btn btn-primary w-auto mt-4">Edit</button>
                                <button onclick="removeTiming(${timing.id})" class="btn btn-danger w-auto mt-4">Remove</button>
                            </td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <jsp:include page="/footer.jsp"/>
    </div>
</div

<script>

    function editTiming(id) {
        window.location.replace("/timing.do?edit=" + id);
    }

    function removeTiming(id) {
        fetch("/rest/timing/" + id, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    // Redirect if deletion was successful
                    window.location = "/timing.do";
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