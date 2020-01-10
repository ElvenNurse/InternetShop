<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="orders" scope="request" type="java.util.List<mate.academy.internetshop.model.Order>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>
<table border="1">
    <tr>
        <th>Order ID</th>
        <th>Items</th>
        <th>Delete Order</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}" />
            </td>
            <td>
                <table border="1">
                    <tr>
                        <th>Item Name</th>
                        <th>Price</th>
                    </tr>
                    <c:forEach var="item" items="${order.items}">
                        <tr>
                            <td>
                                <c:out value="${item.name}" />
                            </td>
                            <td>
                                <c:out value="${item.price}" />
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/servlet/deleteUserOrder?order_id=${order.id}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<h3><a href="${pageContext.request.contextPath}/shop">Return to SHOP</a></h3><br>
<h3><a href="${pageContext.request.contextPath}/index">Return to Main Page</a></h3>
</body>
</html>
