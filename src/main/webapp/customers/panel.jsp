<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="../assets/css/boot.css">
    <link rel="stylesheet" href="../assets/css/UI.css">
    <style>
        /* Custom style to fit 5 shows in a row */
        .services-card {
            width: 20%; /* Each show takes 20% of the row (1/5th) */
        }
    </style>
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
<div id="image-count" style="height: 110em;">
    <img src="/assets/images/mainpic.jpg" style="width:100%" alt="">
    <div class="container">
        <div class="caption-site" >
            <div class="container top-header" style=" direction: rtl !important;">
<%--                <div class="search-box d-lg-flex">--%>
<%--                    <div class="input-group md-form form-sm form-1 pl-0">--%>
<%--                        <input class="form-control mr-sm-2" type="search" placeholder="Search Salon..." aria-label="Search"--%>
<%--                               oninput="findSalonByText(this.value)">--%>
<%--                        <div class="input-group-prepend" style="padding-bottom: 2px;" >--%>
<%--                    <span style="background-color: black; border-radius: 8px;" class="input-group-text purple lighten-3"--%>
<%--                          id="basic-text1"><svg style="color: #fff;" width="1em" height="1em" viewBox="0 0 16 16"--%>
<%--                                                class="bi bi-search" fill="currentColor" xmlns="http://www.w3.org/2000/svg">--%>
<%--                            <path fill-rule="evenodd"--%>
<%--                                  d="M10.442 10.442a1 1 0 0 1 1.415 0l3.85 3.85a1 1 0 0 1-1.414 1.415l-3.85-3.85a1 1 0 0 1 0-1.415z" />--%>
<%--                            <path fill-rule="evenodd"--%>
<%--                                  d="M6.5 12a5.5 5.5 0 1 0 0-11 5.5 5.5 0 0 0 0 11zM13 6.5a6.5 6.5 0 1 1-13 0 6.5 6.5 0 0 1 13 0z" />--%>
<%--                        </svg></span>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
            <h1 class="d-lg-block" style="text-align: center; font-weight:bolder; font-size:6em;">Beauty Salon</h1>
<%--             <p style="text-align: justify;"></p>--%>
<%--            <img src="/assets/images/mainpic.jpg" class="caption-back d-none d-lg-flex" alt="">--%>

    <div>
        <p class="hero-header">New Salons</p>
        <hr style="border-top: 3px solid rgba(0, 0, 0, 0.1); margin-top: -18px;">
        <a href="search.do" class="btn btn-dark moredore"
           style="float: left; margin-top: -40px; color: #fff; font-size: 12px;">View More</a>
    </div>
    <hr style="border-top: 3px solid rgba(0, 0, 0, 0.1); margin-top: -19px; margin-bottom: -0;">

    <div class="row" style="margin-right: 0; margin-left: 0; width: 100%;">

        <c:forEach var="salon" items="${sessionScope.allActiveSalon}">
            <div class="col-12 col-md-6 col-lg-4 post-item">
                <a onclick="selectSalon(${salon.id})">
                    <div class="image-item">
                        <c:choose>
                            <c:when test="${not empty salon.attachments}">
                                <img src="${salon.attachments.get(0).fileName}" class="card-img-top d-block mx-auto" alt="Salon Poster" style="height: 280px">
                            </c:when>
                            <c:otherwise>
                                <div class="card-img-top d-block mx-auto text-center py-5" style="background-color: #f0f0f0; height: 280px;">No Image</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </a>

                <div class="caption-item-dark" style="height:11em; background-color: #1c1c1c; border-radius:15px; margin-top:-35px; position:relative;">
                    <a onclick="selectSalon(${salon.id})">
                        <div class="title-item align-pas">
                            <h5 class="card-title text-center">${salon.name}</h5>
                        </div>
                    </a>

                    <div class="cap-item align-pas">
                        <p class="text-center">${salon.description}</p>
                    </div>

<%--                    <div class="text-center">--%>
<%--                        <button class="btn btn-primary" onclick="selectSalon(${salon.id})">Select</button>--%>
<%--                    </div>--%>

                </div>
            </div>
        </c:forEach>
    </div>




            </div>
        </div>
    </div>
</div>



<jsp:include page="/./footer.jsp"/>

<script>
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

    // const newIds = [1,2,3];

    function selectSalon(id){
        window.location.replace("/salonHome.do?selectSalon=" + id);
    }

    // newIds.forEach(id => {selectSalon(id);})

    let timeout = null;
    function findSalonByText(showText) {
        clearTimeout(timeout);
        timeout = setTimeout(function () {
            window.location.replace("/search.do?selectSalon=" + encodeURIComponent(showText));
        }, 500);  // 300ms debounce delay
    }
</script>
</body>
<jsp:include page="/js-include.jsp"/>

</html>
