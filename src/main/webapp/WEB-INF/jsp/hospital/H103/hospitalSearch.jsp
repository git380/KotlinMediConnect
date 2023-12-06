<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>他病院住所検索</title>
</head>
<body>
<h1>他病院住所検索</h1>
<form action="HospitalSearchServlet" method="post">
    <label for="address">住所:</label>
    <input type="text" id="address" name="address"><br>
    <input type="submit" value="検索">
</form>
</body>
</html>
