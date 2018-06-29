<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <s:property value="[2].top.page.totalRecord"/><br/>
    ${abc[0]}<br/>
    ${abc[1]}<br/>
    ${abc[2]}<br/>
    <s:debug/>
</body>
</html>
