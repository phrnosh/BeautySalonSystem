<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Services Editing</title>

    <jsp:include page="../css-include.jsp"/>

</head>
<body>

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>

        <div class="content flex-grow-1 ">

            <div class="d-flex align-items-center h-100">

                <div class="bg-secondary p-5 w-25" style="margin-left: 12%">

                    <div class="mb-5">

                        <form id="edit-form">

                            <div class="form-row">
                                <div class="form-group col-md-3">
                                    <label for="id">ID</label>
                                    <input type="number" class="form-control" id="id" name="id" value="${sessionScope.editingServices.id}" disabled>
                                </div>
                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="name">Name</label>
                                    <input type="text" class="form-control" id="name" name="name" value="${sessionScope.editingServices.name}" disabled>
                                </div>
                            </div>

                            <div class="form-row">

                                <div class="form-group col-md-8">
                                    <label for="status">Status</label>
                                    <select name="status" class="form-control" id="status">
                                        <option value="true">active</option>
                                        <option value="false">not active</option>
                                    </select>
                                </div>

                                <div class="form-group col-md-8">
                                    <label for="servicesType">Type</label>
                                    <select name="servicesType" class="form-control" id="servicesType">
                                        <option value="HAIRSTYLE" ${sessionScope.editingServices.servicesType == 'HAIRSTYLE' ? 'selected' : ''}>Hairstyle</option>
                                        <option value="MAKEUP" ${sessionScope.editingServices.servicesType == 'MAKEUP' ? 'selected' : ''}>MakeUp</option>
                                        <option value="SANITARY" ${sessionScope.editingServices.servicesType == 'SANITARY' ? 'selected' : ''}>Sanitary</option>
                                        <option value="NAILS" ${sessionScope.editingServices.servicesType == 'NAILS' ? 'selected' : ''}>Nail</option>
                                    </select>
                                </div>
                                
                            </div>


                            <div class="form-row">

                                <div class="form-group col-md-8">
                                    <label for="stylistName">Director</label>
                                    <input type="text" class="form-control" id="stylistName" name="stylistName" value="${sessionScope.editingServices.stylistName}">
                                </div>

                            </div>


                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="releasedDate">Released Date</label>
                                    <input type="date" class="form-control" id="releasedDate" name="releasedDate" value="${sessionScope.editingServices.releasedDate}">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="description">Description</label>
                                    <input type="text" class="form-control" id="description" name="description" value="${sessionScope.editingServices.description}">
                                </div>
                            </div>
                        </form>
                    </div>

                    <div>
                        <button onclick="editingServices()" class="btn btn-primary w-50">Edit</button>
                        <button onclick="cancelEditingServices(${sessionScope.editingServices.id})" class="btn btn-light w-25">Back</button>
                    </div>


                </div>

                <div class="d-inline p-5 w-25 h-100 d-flex flex-column justify-content-between" style="margin-left: 5%">

                    <div class="d-flex w-100 mb-5 bg-body-secondary justify-content-center">
                        <c:choose>
                            <c:when test="${not empty sessionScope.editingServices.attachments}">
                                <img src="${sessionScope.editingServices.attachments.get(0).fileName}" alt="Services Image" height="300px" width="200px">
                            </c:when>
                            <c:otherwise>
                                No Image
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="d-flex justify-content-center mb-5">
                        <form action="services.do" method="post" enctype="multipart/form-data">
                            <input type="file" name="newImage" class="input-group mb-5">
                            <input type="submit" value="Change Image" class="btn btn-dark">
                        </form>
                    </div>

                </div>


                <div class="d-inline p-5 w-25 h-100 d-flex flex-column justify-content-between" style="margin-left: 1%">

                    <div class="d-flex w-100 mb-5 bg-body-secondary justify-content-center">
                        <c:choose>
                            <c:when test="${fn:length(sessionScope.editingServices.attachments) gt 1}">
                                <img src="${sessionScope.editingServices.attachments.get(1).fileName}" alt="Services Image" height="300px" width="200px">
                            </c:when>
                            <c:otherwise>
                                No Image
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="d-flex justify-content-center mb-5">
                        <form action="services.do" method="post" enctype="multipart/form-data">
                            <input type="file" name="newImage" class="input-group mb-5">
                            <input type="submit" value="Change Image" class="btn btn-dark">
                        </form>
                    </div>

                </div>



                <div class="d-inline p-5 w-25 h-100 d-flex flex-column justify-content-between" style="margin-left: 1%">

                    <div class="d-flex w-100 mb-5 bg-body-secondary justify-content-center">
                        <c:choose>
                            <c:when test="${fn:length(sessionScope.editingServices.attachments) gt 2}">
                                <img src="${sessionScope.editingServices.attachments.get(2).fileName}" alt="Services Image" height="300px" width="200px">
                            </c:when>
                            <c:otherwise>
                                No Image
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="d-flex justify-content-center mb-5">
                        <form action="services.do" method="post" enctype="multipart/form-data">
                            <input type="file" name="newImage" class="input-group mb-5">
                            <input type="submit" value="Change Image" class="btn btn-dark">
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="/footer.jsp"/>
    </div>
</div>

<script>

    async function editingServices() {
        let form = document.getElementById("edit-form");

        // Convert form data to a plain object
        let formDataObj = {};
        new FormData(form).forEach((value, key) => {
            formDataObj[key] = value;
        });

        try {
            // Make the PUT request with JSON data
            const response = await fetch("/services.do", {
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
                alert("Services updated successfully!");

                window.location.href = '/services.do';

            } else {
                // Handle errors
                const errorData = await response.json();
                console.error("Error:", errorData);
                alert("Error updating services: " + errorData.message);

                window.location.href = '/services.do';
            }
        } catch (error) {
            console.error("Request failed:", error);
            alert("An error occurred while updating the services.");
            window.location.href = '/services.do';
        }

    }

    async function cancelEditingServices(id) {

        window.location.replace("/services.do?cancel=" + id)
    }

</script>

<jsp:include page="../js-include.jsp"/>

</body>
</html>