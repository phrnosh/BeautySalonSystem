<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reset Password</title>
    <link rel="stylesheet" href="assets/css/boot.css">
    <link rel="stylesheet" href="assets/css/UI.css">
</head>
<body style="background-color:#f1f1f1">

<br><br><br>
<div class="container d-flex justify-content-center">
    <div class="register-item col-12 col-md-6 col-lg-6 bg-secondary">
        <div class="header-register text-center">
            <h4>Reset Password</h4>
            <br><br>
            <hr style="border-top: 3px solid rgba(0, 0, 0, 0.1); margin-top: -18px;">
        </div>
        <br><br>

        <div class="input-register text-center mx-auto">
            <input class="mb-2" type="text" style="display: block; width: 80%; margin: 0 auto;" placeholder="Enter Username / Phone Number" name="username" id="username">
            <button onclick="resetPassword()" class="btn btn-dark w-25 mt-4">Reset Password</button>


        </div>

        <div class="footer-register text-center mx-auto">
            <a href="postLogin.do" class="btn-reg">Back</a>
        </div>
    </div>
</div>

<br><br><br><br>
<script>

    function resetPassword() {
        const username = document.getElementById("username").value;

        fetch("/rest/user/" + username, {
            method: "GET",
        })
            .then(response => {
                if (response.ok) {
                    return response.text();  // Read the new password as plain text from the response
                } else {
                    return response.text().then(errorMessage => {
                        throw new Error(errorMessage);  // Throw the error message to be handled in the catch block
                    });
                }
            })
            .then(newPassword => {
                alert("Your new password is: " + newPassword);
                window.location = "/postLogin.do";  // Redirect if needed
            })
            .catch(error => {
                alert("Error: " + error.message);
            });
    }



</script>

</body>

<script src="assets/js/jquery-3.7.1.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/bootstrap.bundle.min.js"></script>

</html>