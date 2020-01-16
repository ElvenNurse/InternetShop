<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="is_logged" scope="request" type="java.lang.Integer"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
Welcome to Registration page!<br>
<c:if test = "${is_logged == 0}">
<form action="${pageContext.request.contextPath}/registration" method="post">
    <div class="container">
        <h1>Register</h1>
        <p>Please fill in this form to create an account.</p>
        <hr>

        <label for="username"><b>Username</b></label>
        <input type="text" placeholder="Enter Username" id="username" name="username" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" id="psw" name="psw" required>

        <label for="psw-repeat"><b>Repeat Password</b></label>
        <input type="password" placeholder="Repeat Password" id="psw-repeat" name="psw-repeat" required>

        <label for="firstName"><b>First Name</b></label>
        <input type="text" placeholder="Enter First Name" id="firstName" name="firstName" required>

        <label for="secondName"><b>Second Name</b></label>
        <input type="text" placeholder="Enter Second Name" id="secondName" name="secondName" required>
        <hr>

        <p>By creating an account you agree to our <a href="#">Terms & Privacy</a>.</p>
        <button type="submit">Register</button><br>
        <h4 style="color: red"><b><i>${errorMsg}</i></b></h4>
    </div>

    <div class="container signin">
        <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a>.</p>
    </div>
</form>
</c:if>
<c:if test = "${is_logged == 1}">
    <h3>You are logged in, to register new user -
        <a href="${pageContext.request.contextPath}/login">logout</a></h3>
</c:if>
<h3><a href="${pageContext.request.contextPath}/index">Return to Main Page</a></h3>
</body>
</html>
