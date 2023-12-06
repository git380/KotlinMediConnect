<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理者画面</title>
</head>
<body>
<p>ようこそ<c:out value="${empId}"/>さん</p>
<form action="WelcomeServlet" method="post">
    <input type="submit" value="トップへ">
</form>
</body>
</html>