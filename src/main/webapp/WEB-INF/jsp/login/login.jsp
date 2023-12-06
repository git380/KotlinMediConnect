<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理者画面ログイン</title>
</head>
<body>
<form action="LoginServlet" method="post">
    ユーザーID:<input type="text" name="empId"><br>
    パスワード:<input type="password" name="empPasswd"><br>
    <input type="submit" value="ログイン">
</form>
</body>
</html>