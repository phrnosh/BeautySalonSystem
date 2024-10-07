<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Timing Editing</title>


    <jsp:include page="../css-include.jsp"/>

</head>
<body>

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
                                           value="${sessionScope.editingTiming.id}" disabled>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label>Services</label>
                                    <select name="servicesName" id="servicesName" class="form-control">
                                        <c:forEach var="services" items="${sessionScope.allUsableServices}">
                                            <option value="${services.name}"
                                                    <c:if test="${services.name == sessionScope.editingTiming.services.name}">selected</c:if>>
                                                    ${services.name}
                                            </option>
                                        </c:forEach>
                                    </select>
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
                            </div>

                            <div class="form-row">
                                <div class="form-group col-md-8">
                                    <label for="description">Description</label>
                                    <input type="text" class="form-control" id="description" name="description"
                                           value="${sessionScope.editingTiming.description}">
                                </div>
                            </div>

                        </form>
                    </div>

                    <div class="mt-5">
                        <button onclick="editingTiming()" class="btn btn-primary w-50">Edit</button>
                        <button onclick="cancelEditingTiming(${sessionScope.editingTiming.id})"
                                class="btn btn-light w-25">Back
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="/footer.jsp"/>
    </div>
</div>


<script>

    async function editingTiming() {
        let form = document.getElementById("edit-form");

        // Convert form data to a plain object
        let formDataObj = {};
        new FormData(form).forEach((value, key) => {
            formDataObj[key] = value;
        });

        try {
            // Make the PUT request with JSON data
            const response = await fetch("/timing.do", {
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
                alert("Timing updated successfully!");

                window.location.href = '/timing.do';

            } else {
                // Handle errors
                const errorData = await response.json();
                console.error("Error:", errorData);
                alert("Error updating timing: " + errorData.message);

                window.location.href = '/timing.do';
            }
        } catch (error) {
            console.error("Request failed:", error);
            alert("An error occurred while updating the timing.");
            window.location.href = '/timing.do';
        }
    }

    async function cancelEditingTiming(id) {

        window.location.replace("/timing.do?cancel=" + id)
    }

</script>

<jsp:include page="../js-include.jsp"/>

</body>
</html>