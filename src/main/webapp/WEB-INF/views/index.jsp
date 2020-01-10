<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Index</title>
    </head>
    <body>
    <h2>Hello Visitor!</h2><br>
    <h3><a href="${pageContext.request.contextPath}/shop">Go to SHOP</a></h3><br>
    <h3><a href="${pageContext.request.contextPath}/bucket?user_id=1">Check your bucket</a></h3><br>
    <h3><a href="${pageContext.request.contextPath}/orders?user_id=1">Check your orders</a></h3><br><br><br>
    <h4><a href="${pageContext.request.contextPath}/registration">Registration</a></h4><br><br><br>
    <a href="${pageContext.request.contextPath}/servlet/getAllItems">Add items to shop</a><br>
    <a href="${pageContext.request.contextPath}/servlet/getAllUsers">Check all users</a><br>
    <a href="${pageContext.request.contextPath}/servlet/getAllOrders">Check all orders</a>
    </body>
</html>
