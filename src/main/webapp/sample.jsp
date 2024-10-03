<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Manager Search</title>

    <jsp:include page="/css-include.jsp"/>



    <script>
        // Function to call the API and display the result in a table
        function findManagerByName() {
            var name = document.getElementById("managerName").value;

            // AJAX call to fetch data from the API
            $.ajax({
                url: "http://localhost:8080/yourapp/manager/findByName/" + name,
                method: "GET",
                dataType: "json", // Expect JSON response
                success: function(response) {
                    // Clear previous results
                    $("#resultTable tbody").empty();

                    // Check if there is any data in the response
                    if (response && response.length > 0) {
                        // Loop through the response and create table rows
                        response.forEach(function(manager) {
                            var row = "<tr>" +
                                "<td>" + manager.id + "</td>" +
                                "<td>" + manager.name + "</td>" +
                                "<td>" + manager.family + "</td>" +
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
    </script>
</head>
<body>

<h1>Find Manager by Name</h1>

<!-- Input field to enter the manager name -->
<label for="managerName">Enter Manager Name:</label>
<input type="text" id="managerName" name="managerName" />

<!-- Button to trigger the API call -->
<button onclick="findManagerByName()">Search</button>

<!-- Table to display the result -->
<table id="resultTable" border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Family</th>
    </tr>
    </thead>
    <tbody>
    <!-- Results will be appended here by JavaScript -->
    </tbody>
</table>


<jsp:include page="/js-include.jsp"/>

</body>
</html>