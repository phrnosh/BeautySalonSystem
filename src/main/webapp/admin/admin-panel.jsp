<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Panel</title>
    <jsp:include page="../css-include.jsp"/>

</head>
<body style="background-color:rgba(202, 194, 188);">

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
<%--TODO: check all includes--%>
    <jsp:include page="/admin/admin-sidebar.jsp"/>


    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>


        <div class="content d-flex flex-column align-items-center flex-grow-1">

            <div class="d-flex p-4 w-100">

                <div class="p-5">
<%--                    <i class="fa fa-user mb-3" style="font-size: xxx-large"></i>--%>
<%--                    <h1>Admin</h1>--%>
                    <a> <img class="fa col-12 col-md-9 col-lg-3" style="max-width: 100%; border-radius: 15%; margin:3%; font-size: xxx-large;" src="../assets/images/admin-btn1.jpg" > </a>
                </div>

                <div style="margin-left: 5%">
                    <form action="admin.do" method="post" enctype="multipart/form-data">

                        <div class="d-flex mb-4">



                            <input class="m-1" type="text" name="name" placeholder="Name">

                            <input class="m-1" type="text" name="family" placeholder="Family">

                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="username" placeholder="Username">

                            <input class="m-1" type="text" name="password" placeholder="Password">


                        </div>

                        <div class="d-flex mb-4">

                            <input class="m-1" type="text" name="phoneNumber" placeholder="Phone Number">

                            <input class="m-1" type="text" name="email" placeholder="Email">

                            <input type="file" name="image" class="m-1">


                        </div>


                        <div class="d-flex mb-4">
                            <input class="btn btn-dark m-1 w-25" type="submit" value="Save">
                        </div>

                    </form>

                </div>


            </div>


            <div>
                <h4 class="mb-0">All Admins</h4>
            </div>


            <div class="d-flex justify-content-center p-5 w-100">

                <table id="resultTable" border="1" class="table-light w-100">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Family</th>
                        <th>Username</th>
                        <th>Password</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>Image</th>
                        <th>Operation</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="admin" items="${sessionScope.allAdmins}">

                        <tr>
                            <td>${admin.id}</td>
                            <td>${admin.name}</td>
                            <td>${admin.family}</td>
                            <td>${admin.user.username}</td>
                            <td>${admin.user.password}</td>
                            <td>${admin.phoneNumber}</td>
                            <td>${admin.email}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${not empty admin.attachments}">
                                        <img src="${admin.attachments.get(0).fileName}" alt="Admin Image" height="80px" width="80px">
                                    </c:when>
                                    <c:otherwise>
                                        No Image
                                    </c:otherwise>
                                </c:choose>
                            </td>


                            <td>
                                <button onclick="editAdmin(${admin.id})" class="btn btn-primary w-25 mt-4">Edit</button>
                                <button onclick="removeAdmin(${admin.id})" class="btn btn-danger w-50 mt-4">Remove</button>
                            </td>
                        </tr>

                    </c:forEach>

                    </tbody>

                </table>
            </div>
        </div>
    </div>

</div>


<script>


    function editAdmin(id) {
        window.location.replace("/admin.do?edit=" + id);
    }

    function removeAdmin(id) {
        fetch("/rest/admin/" + id, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    // Redirect if deletion was successful
                    window.location = "/admin.do";
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
<jsp:include page="/footer.jsp"/>


<jsp:include page="../js-include.jsp"/>

</body>
</html>