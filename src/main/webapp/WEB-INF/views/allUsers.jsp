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
<a href="/internet_shop_war_exploded/servlet/registration">Register new user</a><br>
Users :
<table border="1">
    <tr>
        <th>ID</th>
        <th>Username</th>
        <th>First Name</th>
        <th>Second Name</th>
        <th>Delete User</th>
    </tr>
    <c:forEach var="item" items="${users}">
        <tr>
            <td>
                <c:out value="${item.id}" />
            </td>
            <td>
                <c:out value="${item.username}" />
            </td>
            <td>
                <c:out value="${item.firstName}" />
            </td>
            <td>
                <c:out value="${item.secondName}" />
            </td>
            <td>
                <a href="/internet_shop_war_exploded/servlet/deleteUser?user_id=${item.id}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<h3><a href="/internet_shop_war_exploded/index">Return to Main Page</a></h3>
</body>
</html>
