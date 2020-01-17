<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="username" scope="request" type="java.lang.String"/>
<jsp:useBean id="roles" scope="request" type="java.util.Set<mate.academy.internetshop.model.Role>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Index</title>
    </head>
    <body>
        <h2>Hello, ${username}! It's Main Page</h2><br>

        <c:forEach var="role" items="${roles}">
            <c:if test = "${role.roleName == 'USER'}">
                <div id="user_menu">
                    <jsp:include page="userMenu.jsp"/>
                </div>
            </c:if>
            <c:if test = "${role.roleName == 'ADMIN'}">
                <div id="admin_menu">
                    <jsp:include page="adminMenu.jsp"/>
                </div>
            </c:if>
        </c:forEach>
        <h4><a href="${pageContext.request.contextPath}/logout">Logout</a></h4><br>
    </body>
</html>
