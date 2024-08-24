<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="/assets/css/boot.css">
    <link rel="stylesheet" href="/assets/css/UI.css">
</head>
<body style="background-color:rgba(200, 161, 132, 0.64)">

<jsp:include page="/./short-user-info.jsp"/>
<jsp:include page="/./navbar.jsp"/>
<br>
<!-- image countent -->
<div id="image-count" >
    <img src="/assets/images/mainpic.jpg" style="width:100%" alt="">
    <div class="container">
        <div class="caption-site" >
            <div class="container top-header">
                <div class="search-box d-lg-flex">
                    <div class="input-group md-form form-sm form-1 pl-0">
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
            </div>
        </div>
    </div>
</div>

<br>
</body>
<jsp:include page="/js-include.jsp"/>
</html>
