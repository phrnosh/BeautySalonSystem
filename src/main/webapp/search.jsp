<%@ page import="com.beautysalon.beautysalonsystem.model.entity.Attachment" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show Select</title>
    <jsp:include page="/css-include.jsp"/>

    <style>
        /* Custom style to fit 5 shows in a row */
        .services-card {
            width: 20%; /* Each show takes 20% of the row (1/5th) */
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

    <div class="d-flex p-4 w-100 ">

        <div class="p-5 ">
            <i class="fa fa-search mb-3" style="font-size: xxx-large"></i>
            <h1>Find your Services</h1>
        </div>

        <div class=" w-50 align-content-center" style="margin-left: 5%">

            <input class="input-group m-2" type="text" name="servicesText" placeholder="Search Service"
                   oninput="findServicesByText(this.value)">
            <%--            <button class="btn btn-primary" onclick="findShowByText(this.value)">Find</button>--%>

        </div>


    </div>

    <div class="mb-auto justify-content-center d-flex">


        <div class="container">
            <div class="row">
                <c:forEach var="services" items="${sessionScope.allFoundServices}">
                    <div class="services-card mb-5 p-2"> <!-- Each show occupies 1/5th of the row -->
                        <div class="card h-100"> <!-- Card layout for each show -->
                            <c:choose>
                                <c:when test="${not empty services.attachments}">
                                    <img src="${services.attachments.get(0).fileName}" class="card-img-top d-block mx-auto" alt="Services image" style="width: 190px; height: 280px">
                                </c:when>
                                <c:otherwise>
                                    <div class="card-img-top d-block mx-auto text-center py-5" style="background-color: #f0f0f0; width: 190px; height: 280px;">No Image</div>
                                </c:otherwise>
                            </c:choose>
                            <div class="card-body">
                                <h5 class="card-title text-center">${services.name}</h5>
                                <div class="text-center">
                                    <button class="btn btn-primary" onclick="selectServices(${services.id})">Select</button>
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
    function selectServices(id){
        window.location.replace("/salonHome.do?selectServices=" + id);
    }

    let timeout = null;
    function findServicesByText(showText) {
        clearTimeout(timeout);
        timeout = setTimeout(function () {
            window.location.replace("/search.do?servicesText=" + encodeURIComponent(showText));
        }, 500);  // 300ms debounce delay
    }


</script>

<jsp:include page="/js-include.jsp"/>

</body>
</html>