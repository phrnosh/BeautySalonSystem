<%@ page import="com.beautysalon.beautysalonsystem.model.entity.Attachment" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Salon Search</title>
    <jsp:include page="/css-include.jsp"/>

    <style>
        /* Custom style to fit 5 shows in a row */
        .services-card {
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

    <div class="d-flex p-4 w-100 ">

        <div class="p-5 ">
            <i class="fa fa-search mb-3" style="font-size: xxx-large"></i>
            <h1>Find your Salons</h1>
        </div>

        <div class=" w-50 align-content-center" style="margin-left: 5%">

            <input class="form-control mr-sm-2 input-group m-2" type="text" name="salonText" placeholder="Search Salon"
                   oninput="findSalonByText(this.value)">
            <%--            <button class="btn btn-primary" onclick="findShowByText(this.value)">Find</button>--%>

        </div>


    </div>

    <div class="mb-auto justify-content-center d-flex">
        <div class="container">
            <div class="row" style="margin-right: 0; margin-left: 0; width: 100%;">
                <c:forEach var="salon" items="${sessionScope.allFoundSalons}">
                    <div class="col-12 col-md-6 col-lg-4 post-item">
<%--                    <div class="salon-card mb-5 p-2"> <!-- Each show occupies 1/5th of the row -->--%>
<%--                        <div class="card h-100"> <!-- Card layout for each show -->--%>
                        <a onclick="selectSalon(${salon.id})">
                            <div class="image-item">
                            <c:choose>
                                <c:when test="${not empty salon.attachments}">
                                    <img src="${salon.attachments.get(0).fileName}" class="card-img-top d-block mx-auto" alt="Salon image" style=" height: 280px">
                                </c:when>
                                <c:otherwise>
                                    <div class="card-img-top d-block mx-auto text-center py-5" style="background-color: #f0f0f0; height: 280px;">No Image</div>
                                </c:otherwise>
                            </c:choose>
                            </div>
                        </a>

                        <div class="caption-item" style="height:11em;">
                            <a onclick="selectSalon(${salon.id})">
                                <div class="title-item align-pas">
                                    <h5 class="card-title text-center">${salon.name}</h5>
                                </div>
                            </a>

                            <div class="cap-item align-pas">
                                <p class="text-center">${salon.description}</p>
                            </div>
<%--                            <div class="card-body">--%>
<%--                                <h5 class="card-title text-center">${salon.name}</h5>--%>
<%--                                <div class="text-center">--%>
<%--                                    <button class="btn btn-primary" onclick="selectSalon(${salon.id})">Select</button>--%>
<%--                                </div>--%>
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
    function selectSalon(id){
        window.location.replace("/salonHome.do?selectSalon=" + id);
    }

    let timeout = null;
    function findSalonByText(salonText) {
        clearTimeout(timeout);
        timeout = setTimeout(function () {
            window.location.replace("/search.do?salonText=" + encodeURIComponent(salonText));
        }, 500);  // 300ms debounce delay
    }


</script>

<jsp:include page="/js-include.jsp"/>

</body>
</html>