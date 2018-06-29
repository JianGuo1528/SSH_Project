<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
    <ul>
        <c:forEach items="${roles}" var="r">
            <c:forEach items="${r.modules}" var="m">
                <li>${m.name}</li>
            </c:forEach>
        </c:forEach>
        <%--<li>${userName}</li>--%>
    </ul>
</body>
</html>
