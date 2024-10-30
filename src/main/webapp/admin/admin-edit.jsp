<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Editing</title>


    <jsp:include page="../css-include.jsp"/>

</head>
<body style="background-color:rgba(202, 194, 188, 0.4);">

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>

        <div class="content flex-grow-1 ">

            <div class="d-flex align-items-center h-100">

                <div class="bg-secondary h-100 p-5 w-25" style="margin-left: 12%">

                    <div class="mb-5">
                        <form id="edit-form">

                            <div class="form-row">
                                <div class="form-group col-md-3">
                                    <label for="id">ID</label>
                                    <input type="number" class="form-control" id="id" name="id"
                                           value="${sessionScope.editingAdmin.id}" disabled>
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="name">Name</label>
                                    <input type="text" class="form-control" id="name" name="name" value="${sessionScope.editingAdmin.name}">
                                </div>
                                <div class="form-group col-md-8">
                                    <label for="family">Family</label>
                                    <input type="text" class="form-control" id="family" name="family" value="${sessionScope.editingAdmin.family}">
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="phoneNumber">Phone Number</label>
                                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${sessionScope.editingAdmin.phoneNumber}">
                                </div>
                                <div class="form-group col-md-8">
                                    <label for="email">Email</label>
                                    <input type="text" class="form-control" id="email" name="email" value="${sessionScope.editingAdmin.email}">
                                </div>

                            </div>

                        </form>
                    </div>

                    <div class="mt-5">
                        <button onclick="editingAdmin()" class="btn btn-primary w-50">Edit</button>
                        <button onclick="cancelEditingAdmin(${sessionScope.editingAdmin.id})"
                                class="btn btn-light w-25">Back
                        </button>
                    </div>

                </div>

                <div class="d-inline p-3 w-25 h-75 d-flex flex-column justify-content-between" style="margin-left: 10%">
                    <div class="d-flex w-100 mb-5 justify-content-center">
                        <c:choose>
                            <c:when test="${not empty sessionScope.editingAdmin.attachments}">
                                <img src="${sessionScope.editingAdmin.attachments.get(0).fileName}" alt="Admin Image" height="80px" width="80px">
                            </c:when>
                            <c:otherwise>
                                No Image
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="d-flex justify-content-center mb-5">
                        <form action="admin.do" method="post" enctype="multipart/form-data">
                            <input type="file" name="newImage" class="input-group mb-5">
                            <input type="submit" value="Change Image" class="btn btn-dark">
                        </form>
                    </div>

                </div>
            </div>
        </div>

    </div>
</div>


<script>

    async function editingAdmin() {
        let form = document.getElementById("edit-form");

        // Convert form data to a plain object
        let formDataObj = {};
        new FormData(form).forEach((value, key) => {
            formDataObj[key] = value;
        });

        try {
            // Make the PUT request with JSON data
            const response = await fetch("/admin.do", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formDataObj)  // Send the form data as JSON
            });

            // Check if the request was successful
            if (response.ok) {
                const data = await response.json(); // Parse the JSON response
                console.log("Success:", data);

                // Display success feedback to the user
                alert("Admin updated successfully!");

                window.location.href = '/admin.do';

            } else {
                // Handle errors
                const errorData = await response.json();
                console.error("Error:", errorData);
                alert("Error updating admin: " + errorData.message);

                window.location.href = '/admin.do';
            }
        } catch (error) {
            console.error("Request failed:", error);
            alert("An error occurred while updating the admin.");
            window.location.href = '/admin.do';
        }

    }


    async function cancelEditingAdmin(id) {

        window.location.replace("/admin.do?cancel=" + id)
    }


</script>
<jsp:include page="/footer.jsp"/>


<jsp:include page="../js-include.jsp"/>

</body>
</html>