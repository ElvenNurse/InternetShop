<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.model.Item>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Welcome to <b>The Best Internet Shop</b> in the World! :)<br>
Items :
<table border="1">
    <tr>
        <th>Item Name</th>
        <th>Price</th>
        <th>Add To Bucket</th>
    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>
                <c:out value="${item.name}" />
            </td>
            <td>
                <c:out value="${item.price}" />
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/servlet/addToBucket?item_id=${item.id}">ADD</a>
            </td>
        </tr>
    </c:forEach>
</table>
<h3><a href="${pageContext.request.contextPath}/user/bucket">Check your bucket</a></h3><br>
<h3><a href="${pageContext.request.contextPath}/index">Return to Main Page</a></h3>
</body>
</html>
