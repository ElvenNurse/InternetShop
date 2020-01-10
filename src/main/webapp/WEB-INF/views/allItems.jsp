<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.model.Item>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Items</title>
</head>
<body>
Welcome to All Items page!<br>
<form action="${pageContext.request.contextPath}/servlet/addItem" method="get">
    <div class="container">
        <h3>Add Item</h3>
        <p>Please fill in this form to add item.</p>
        <hr>

        <label for="itemName"><b>Item Name</b></label>
        <input type="text" placeholder="Enter Item Name" id="itemName" name="itemName" required>

        <label for="price"><b>Price</b></label>
        <input type="text" placeholder="Enter Price" id="price" name="price" required>
        <hr>

        <button type="submit">Add item</button>
    </div>
</form>
<h3><a href="${pageContext.request.contextPath}/index">Return to Main Page</a></h3>
All Items :
<table border="1">
    <tr>
        <th>ID</th>
        <th>Item Name</th>
        <th>Price</th>
        <th>Delete Item</th>
    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>
                <c:out value="${item.id}" />
            </td>
            <td>
                <c:out value="${item.name}" />
            </td>
            <td>
                <c:out value="${item.price}" />
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/servlet/deleteItem?item_id=${item.id}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
