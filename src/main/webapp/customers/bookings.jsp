<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your Bookings</title>

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

    <jsp:include page="/customers/customer-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content d-flex flex-column align-items-center flex-grow-1">

            <div class="p-5 ">
                <i class="fa fa-booking mb-3" style="font-size: xxx-large"></i>
                <h1>Your Booking</h1>
            </div>


            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th hidden="hidden">ID</th>
                        <th>Services</th>
                        <th>Date</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Salon</th>
                        <th>Address</th>
                        <th>Name</th>
                        <th>Family</th>
                        <th>Phone Number</th>
                        <th>Verified</th>
                        <th>Issue Time</th>
                        <th>Description</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="booking" items="${sessionScope.customerBooking}">

                        <tr>
                            <td hidden="hidden">${booking.id}</td>
                            <td>${booking.servicesName}</td>
                            <td>${booking.servicesDate}</td>
                            <td>${booking.startHour}</td>
                            <td>${booking.endHour}</td>
                            <td>${booking.salonName}</td>
                            <td>${booking.address}</td>
                            <td>${booking.customerName}</td>
                            <td>${booking.customerFamily}</td>
                            <td>${booking.customerPhoneNumber}</td>
                            <td>${booking.verified}</td>
                            <td>${booking.issueTime}</td>
                            <td>${fn:substring(booking.description, 0, 10)}...</td>

                            
                            <td>
                                <button onclick="printBooking(${booking.id})" class="btn btn-primary w-100 mt-4">Print</button>
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


    function printBooking(id) {
        window.location.replace("/booking.do?print=" + id);
    }
    
</script>
<jsp:include page="../js-include.jsp"/>

</body>
</html>