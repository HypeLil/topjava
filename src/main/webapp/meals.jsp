<%--
  Created by IntelliJ IDEA.
  User: liltu
  Date: 23.02.2021
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<c:forEach var="m" items="${meal}">
    <p>${m}</p>
</c:forEach>
</body>
</html>
