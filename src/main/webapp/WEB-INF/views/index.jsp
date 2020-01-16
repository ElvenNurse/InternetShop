<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Index</title>
    </head>
    <body>
        <h2>Hello, ${username}! It's Main Page</h2><br>
        <c:if test = "${is_user == 1}">
            <h3><a href="${pageContext.request.contextPath}/shop">Go to SHOP</a></h3>
            <h3><a href="${pageContext.request.contextPath}/bucket">Check your bucket</a></h3>
            <h3><a href="${pageContext.request.contextPath}/orders">Check your orders</a></h3><br>
        </c:if>
        <c:if test = "${is_admin == 1}">
            <h3><a href="${pageContext.request.contextPath}/servlet/getAllItems">Add items to shop</a></h3>
            <h3><a href="${pageContext.request.contextPath}/servlet/getAllUsers">Check all users</a></h3>
            <h3><a href="${pageContext.request.contextPath}/servlet/getAllOrders">Check all orders</a></h3><br>
        </c:if>
        <h4><a href="${pageContext.request.contextPath}/login">Logout</a></h4><br>
    </body>
</html>
