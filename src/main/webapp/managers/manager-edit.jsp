<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager Editing</title>

    <jsp:include page="../css-include.jsp"/>

</head>
<body>

<div class="container-fluid d-flex flex-row vh-100 p-0">

    <div class="content d-flex flex-column flex-grow-1">

        <jsp:include page="/navbar.jsp"/>

        <div class="content flex-grow-1 ">

            <div class="d-flex">

                <div class="bg-secondary p-5 w-25" style="margin-left: 12%">
                    <form id="edit-form">

                        <div class="form-row">
                            <div class="form-group col-md-3">
                                <label for="id">ID</label>
                                <input type="number" class="form-control" id="id" name="id" value="${sessionScope.editingManager.id}" disabled>
                            </div>
                        </div>


                        <div class="form-row">
                            <div class="form-group col-md-8">
                                <label for="name">Name</label>
                                <input type="text" class="form-control" id="name" name="name" value="${sessionScope.editingManager.name}">
                            </div>
                            <div class="form-group col-md-8">
                                <label for="family">Family</label>
                                <input type="text" class="form-control" id="family" name="family" value="${sessionScope.editingManager.family}">
                            </div>
                        </div>


                        <div class="form-row">
                            <div class="form-group col-md-8">
                                <label for="phoneNumber">Phone Number</label>
                                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${sessionScope.editingManager.phoneNumber}">
                            </div>
                            <div class="form-group col-md-8">
                                <label for="email">Email</label>
                                <input type="text" class="form-control" id="email" name="email" value="${sessionScope.editingManager.email}">
                            </div>
                            <div class="form-group col-md-8">
                                <label for="nationalCode">National Code</label>
                                <input type="text" class="form-control" id="nationalCode" name="nationalCode" value="${sessionScope.editingManager.nationalCode}">
                            </div>
                        </div>


                        <div class="form-row">
                            <div class="form-group col-md-12">
                                <label for="address">Address</label>
                                <input type="text" class="form-control" id="address" name="address" value="${sessionScope.editingManager.address}">
                            </div>
                        </div>
                    </form>
                </div>

                <div class="d-inline p-3 w-25" style="margin-left: 10%">
                    <div class="d-flex w-100 mb-5 justify-content-center">
                        <img class="bg-secondary" src="" alt="No Picture" style="width: 150px; height: 150px">
                    </div>
                    <button onclick="editManager()" class="btn btn-primary w-50">Edit</button>
                    <button onclick="cancelEditingManager(${sessionScope.editingManager.id})" class="btn btn-secondary w-25">Back</button>
                </div>


            </div>


        </div>

        <jsp:include page="/footer.jsp"/>
    </div>
</div>


<script>

    async function editManager() {
        let form = document.getElementById("edit-form");

        // Convert form data to a plain object
        let formDataObj = {};
        new FormData(form).forEach((value, key) => {
            formDataObj[key] = value;
        });

        try {
            // Make the PUT request with JSON data
            const response = await fetch("/manager.do", {
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
                alert("Manager updated successfully!");

                window.location.href = '/manager.do';

            } else {
                // Handle errors
                const errorData = await response.json();
                console.error("Error:", errorData);
                alert("Error updating manager: " + errorData.message);

                window.location.href = '/manager.do';
            }
        } catch (error) {
            console.error("Request failed:", error);
            alert("An error occurred while updating the manager.");
            window.location.href = '/manager.do';
        }

    }


    async function cancelEditingManager(id) {

        window.location.replace("/manager.do?cancel=" + id)
    }


</script>


<jsp:include page="../js-include.jsp"/>

</body>
</html>
