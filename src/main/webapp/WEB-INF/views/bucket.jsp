<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="bucket" scope="request" type="mate.academy.internetshop.model.Bucket"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bucket</title>
</head>
<body>
<h2>Welcome, <b>${user_name}</b>, to yor bucket!</h2><br><br>
<h3>Items in your bucket:</h3>
<table border="1">
    <tr>
        <th>Item Name</th>
        <th>Price</th>
        <th>Delete Item</th>
    </tr>
    <c:forEach var="item" items="${bucket.items}">
        <tr>
            <td>
                <c:out value="${item.name}" />
            </td>
            <td>
                <c:out value="${item.price}" />
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/servlet/deleteFromBucket?item_id=${item.id}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<h3><a href="${pageContext.request.contextPath}/servlet/completeOrder">Complete order</a></h3>
<h3><a href="${pageContext.request.contextPath}/user/shop">Return to SHOP</a></h3><br>
<h3><a href="${pageContext.request.contextPath}/index">Return to Main Page</a></h3>
</body>
</html>
