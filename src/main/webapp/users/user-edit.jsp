<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Editing</title>

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
                                <div class="form-group col-md-8">
                                    <label for="username">Username</label>
                                    <input type="text" class="form-control" id="username" name="username" value="${sessionScope.editingUser.username}" disabled>
                                </div>

                                <div class="form-group col-md-8">
                                    <label for="password">Password</label>
                                    <input type="text" class="form-control" id="password" name="password" value="${sessionScope.editingUser.password}">
                                </div>

                                <div class="form-group col-md-8">
                                    <label for="role">Role</label>
                                    <input type="text" class="form-control" id="role" name="role" value="${sessionScope.editingUser.role.role}" disabled>
                                </div>

                                <div class="form-group col-md-8">
                                    <label for="locked">Type</label>
                                    <select name="locked" class="form-control" id="locked">
                                        <option value="false" ${sessionScope.editingUser.locked == 'false' ? 'selected' : ''}>Unlocked</option>
                                        <option value="true" ${sessionScope.editingUser.locked == 'true' ? 'selected' : ''}>Locked</option>
                                    </select>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div>
                        <button onclick="editingUser()" class="btn btn-primary w-50">Edit</button>
                        <button onclick="cancelEditingUser('${sessionScope.editingUser.username}')" class="btn btn-light w-25">Back</button>
                    </div>

                </div>
            </div>
        </div>
        <jsp:include page="/footer.jsp"/>
    </div>
</div>


<script>

    async function editingUser() {
        let form = document.getElementById("edit-form");

        // Convert form data to a plain object
        let formDataObj = {};
        new FormData(form).forEach((value, key) => {
            formDataObj[key] = value;
        });

        try {
            // Make the PUT request with JSON data
            const response = await fetch("/user.do", {
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
                alert("User updated successfully!");

                window.location.href = '/login.do';

            } else {
                // Handle errors
                const errorData = await response.json();
                console.error("Error:", errorData);
                alert("Error updating user: " + errorData.message);

                window.location.href = '/login.do';
            }
        } catch (error) {
            console.error("Request failed:", error);
            alert("An error occurred while updating the user.");
            window.location.href = '/login.do';
        }
    }

    async function cancelEditingUser(username) {

        window.location.replace("/user.do?cancel=" + username)
    }

</script>

<jsp:include page="../js-include.jsp"/>

</body>
</html>