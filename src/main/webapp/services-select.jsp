<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Services Select</title>
    <jsp:include page="/css-include.jsp"/>

    <style>
        /* Custom style to fit 5 shows in a row */
        .show-card {
            width: 20%; /* Each show takes 20% of the row (1/5th) */
        }
    </style>

</head>
<body class="content">

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


<div class="d-flex flex-column flex-grow-1 h-100">

    <jsp:include page="/navbar.jsp"/>

    <div class="mt-5 mb-3">
        <div class="item-icon "><i class="fa" style="font-size: xxx-large;"></i></div>
        <h1 class="p-4 text-center">Select Services</h1>

<%--        <a class="btn-reg w-25 text-decoration-none text-black m-3" href="salonHome.do">All</a>--%>

<%--        <button onclick="findHairstyle()" class="btn-reg border-0 m-3">Hairstyle</button>--%>
<%--        <button onclick="findMakeup()" class="btn-reg border-0 m-3">Makeup</button>--%>
<%--        <button onclick="findSanitary()" class="btn-reg border-0 m-3">Sanitary</button>--%>
<%--        <button onclick="findNails()" class="btn-reg border-0 m-3">Nails</button>--%>

    </div>

    <div class="mb-auto justify-content-center d-flex">
        <div class="container">
            <div class="row" style="margin-right: 0; margin-left: 0; width: 100%;">
                <c:forEach var="services" items="${sessionScope.services}">
                    <div class="col-12 col-md-6 col-lg-4 post-item">
                        <div class="image-item">
                            <c:choose>
                                <c:when test="${not empty services.attachments}">
                                    <img src="${services.attachments.get(0).fileName}" class="card-img-top d-block mx-auto" alt="Show Poster" style="height: 280px">
                                </c:when>
                                <c:otherwise>
                                    <div class="card-img-top d-block mx-auto text-center py-5" style="background-color: #f0f0f0; height: 280px;">No Image</div>
                                </c:otherwise>
                            </c:choose>

                            <div class="caption-item" style="height:11em;">
                                <div class="title-item align-pas">
                                    <h5 class="card-title text-center">${services.name}</h5>
                                </div>

                                <div class="cap-item align-pas">
                                    <p class="text-center">${services.stylistName}</p>
                                </div>

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
    <br><br>
    <jsp:include page="/footer.jsp"/>
</div>

<script>
    function selectServices(id){
        window.location.replace("salonHome.do?selectServices=" + id);
    }

    function findHairstyle() {
        window.location.replace("salonHome.do?findHairstyle=" + 1);
    }

    function findMakeup() {
        window.location.replace("salonHome.do?findMakeup=" + 1);
    }

    function findSanitary() {
        window.location.replace("salonHome.do?findSanitary=" + 1);
    }

    function findNails() {
        window.location.replace("salonHome.do?findNails=" + 1);
    }

</script>

<jsp:include page="/js-include.jsp"/>

</body>
</html>