<%--
  Created by IntelliJ IDEA.
  User: huaiwang
  Date: 8/21/13
  Time: 8:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html>
<head>
    <title>JSTL Usage</title>
    <style type="text/css">
        body {
            font-family: "Courier New";
            font-size: 12px;
        }
    </style>
</head>
<body>
<c:forEach items="${keys}" var="key">
    <c:set var="val" value="${map[key]}"></c:set>
    <c:out value="${key}"/>: <c:out value="${val}"/>,
    <br/>
</c:forEach>
</body>
</html>