<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/login_test.action" method="post">
        用户名:<label for="username"></label><input type="text" name="username" id="username">
        <br>
        密码:<label for="password"></label><input type="password" name="password" id="password">
        <br>
        <input type="submit" value="提交">
    </form>
</body>
</html>
