<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:useBean id="users" scope="request" type="java.util.List<mate.academy.internetshop.model.User>"/>
<jsp:useBean id="greeting" scope="request" type="java.lang.String"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users</title>
</head>
<body>
<b>${greeting}</b>, welcome to All Users page!<br>
Users :
<table border="1">
    <tr>
        <th>ID</th>
        <th>Username</th>
        <th>First Name</th>
        <th>Second Name</th>
        <th>User Roles</th>
        <th>Delete User</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.id}" />
            </td>
            <td>
                <c:out value="${user.username}" />
            </td>
            <td>
                <c:out value="${user.firstName}" />
            </td>
            <td>
                <c:out value="${user.secondName}" />
            </td>
            <td>
                <c:forEach var="role" items="${user.roles}">
                    <c:out value="${role.roleName}" /><br>
                </c:forEach>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/servlet/deleteUser?user_id=${user.id}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="${pageContext.request.contextPath}/servlet/addUser" method="post">
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

        <button type="submit">Register</button>
    </div>
</form>
<h3><a href="${pageContext.request.contextPath}/index">Return to Main Page</a></h3>
</body>
</html>
