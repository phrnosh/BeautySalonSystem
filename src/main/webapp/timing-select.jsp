<%@ page import="com.beautysalon.beautysalonsystem.model.entity.enums.ServicesType" %>
<%@ page import="com.beautysalon.beautysalonsystem.model.entity.Services" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Timing Select</title>

    <jsp:include page="/css-include.jsp"/>

    <style>
        /* Custom style to fit 5 shows in a row */
        .show-card {
            width: 25%; /* Each show takes 20% of the row (1/5th) */
        }
    </style>

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


<div class="content d-flex flex-column flex-grow-1 h-100">

    <jsp:include page="/navbar.jsp"/>

    <div class="bg-dark h-50 d-flex flex-row p-5">
        <div>
            <img src="${sessionScope.selectedServices.attachments.get(0).fileName}" alt="No Image" height="280px"
                 width="190px">
        </div>

        <div class="text-white  d-flex flex-column justify-content-between" style="margin-left: 5%">
            <div class="mb-2">
                <h2 style="text-align: left">${sessionScope.selectedServices.name}</h2>
            </div>

            <div>
                <%
                    Services services = (Services) session.getAttribute("selectedServices");
                    if (services.getServicesType().equals(ServicesType.HAIRSTYLE)) {
                %>

                <div class="mb-4">
                    <h5 style="text-align: left">Haircut : ${sessionScope.selectedServices.haircut}</h5>
                </div>

                <div class="mb-4">
                    <h5 style="text-align: left">Styled : ${sessionScope.selectedServices.styled}</h5>
                </div>

                <div class="mb-4">
                    <h5 style="text-align: left">Coloured : ${sessionScope.selectedServices.coloured}</h5>
                </div>

                <%
                } else if (services.getServicesType().equals(ServicesType.MAKEUP)) {
                %>

                <div class="mb-4">
                    <h5 style="text-align: left">Makeup : ${sessionScope.selectedServices.makeup}</h5>
                </div>

                <div class="mb-4">
                    <h5 style="text-align: left">Eyebrows Services : ${sessionScope.selectedServices.eyebrowsservices}</h5>
                </div>

                <div class="mb-4">
                    <h5 style="text-align: left">Eyelash Services : ${sessionScope.selectedServices.eyelashservices}</h5>
                </div>

                <div class="mb-4">
                    <h5 style="text-align: left">lip Services : ${sessionScope.selectedServices.libservices}</h5>
                </div>

                <%
                } else if (services.getServicesType().equals(ServicesType.SANITARY)) {
                %>

                <div class="mb-4">
                    <h5 style="text-align: left">Facial : ${sessionScope.selectedServices.facial}</h5>
                </div>

                <div class="mb-4">
                    <h5 style="text-align: left">Skin Therapy : ${sessionScope.selectedServices.skintherapy}</h5>
                </div>

                <%
                } else if (services.getServicesType().equals(ServicesType.NAILS)) {
                %>

                <div class="mb-4">
                    <h5 style="text-align: left">Manicure : ${sessionScope.selectedServices.manicure}</h5>
                </div>

                <div class="mb-4">
                    <h5 style="text-align: left">Pedicure : ${sessionScope.selectedServices.pedicure}</h5>
                </div>
                <% } %>

                <div>
                    <h5 style="text-align: left">Release Date : ${sessionScope.selectedServices.releasedDate}</h5>
                </div>
            </div>

        </div>


        <div class="text-white p-4 w-25" style="margin-left: 15%">
            <p style="white-space: pre-line; text-align: left;">${sessionScope.selectedServices.description}</p>
        </div>

    </div>


    <div class="bg-secondary d-flex h-25 flex-row align-items-center">
        <div class="bg-secondary p-4 w-25 h-100 text-white">
            <div class="d-flex m-1 flex-row mb-1">
                <i class="fa fa-calendar" style="font-size: xx-large; margin-right: 8px;"></i>
                <h2>Date </h2>
            </div>
            <div class="d-flex">
                <h2>${sessionScope.selectedDate}</h2>
            </div>
        </div>

        <div class="h-100 align-items-center justify-content-between p-3 d-flex flex-row w-75 text-white" style="background-color: #5f5564; margin-left: 10%">
            <div style="margin-left: 2%">
                <div class="d-flex flex-row mb-1">
                    <i class="fa fa-camera-movie" style="font-size: xx-large; margin-right: 8px;"></i>
                    <h2 class="mb-2">Salon</h2>
                </div>

                <div class="d-flex">
                    <h2 class="mb-2">${sessionScope.selectedSalon.name}</h2>
                </div>

            </div>

            <div>

                <div>
                    <div class="d-flex flex-row">
                        <i class="fa fa-map-location-dot" style="font-size: xx-large; margin-right: 8px;"></i>
                        <h4 style="text-align: left">Address </h4>
                    </div>

                    <div>
                        <h5 style="text-align: left">${sessionScope.selectedSalon.address}</h5>
                    </div>
                </div>
            </div>

            <div style="margin-right: 3%">
                <c:choose>
                    <c:when test="${sessionScope.selectedSalon.imageUrl != ''}">
                        <img src="${sessionScope.selectedSalon.imageUrl}" alt="Salon Image" height="150px" width="150px">
                    </c:when>
                    <c:otherwise>
                        No Image
                    </c:otherwise>
                </c:choose>
            </div>

        </div>

    </div>

    <div class="h-75 d-flex p-4 justify-content-center">

        <div class="flex-column">
            <h1 style="text-align: left">Select</h1>
            <h1>Timing</h1>
        </div>


        <div class="d-flex w-100 justify-content-center">
            <div class="row w-75 justify-content-center">
                <c:forEach var="timing" items="${sessionScope.timing}">
                    <div class="show-card m-3 ">
                        <div class="card h-100">
                            <c:choose>
                                <c:when test="${timing.servicesImage != ''}">
                                    <img src="${timing.servicesImage}" class="card-img-top d-block mx-auto"
                                         alt="Salon Image" style="width: 200px; height: 200px">
                                </c:when>
                                <c:otherwise>
                                    <div class="card-img-top d-block mx-auto text-center py-5"
                                         style="background-color: #f0f0f0; width: 200px; height: 200px;">No Image
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="card-body">
                                <h5 class="card-title text-center">${timing.startTime.toLocalTime()}-${timing.endTime.toLocalTime()}</h5>
<%--                                <h5 class="card-title text-center">Capacity : ${timing.remainingCapacity}</h5>--%>
                                <h5 class="card-title text-center">${timing.servicesDescription}</h5>
                                <div class="text-center">
                                    <button class="btn btn-primary" onclick="selectTiming(${timing.id})">Select</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

    </div>
    <jsp:include page="/footer.jsp"/>

</div>


<script>

    function selectTiming(id){
        window.location.replace("/services.do?selectTimingId=" + id);
    }

</script>

<jsp:include page="/js-include.jsp"/>

</body>
</html>