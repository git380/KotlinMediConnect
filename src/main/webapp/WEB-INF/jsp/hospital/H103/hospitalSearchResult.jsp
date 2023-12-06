<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>他病院住所検索結果</title>
</head>
<body>
<h1>他病院住所検索結果</h1>
<c:if test="${not empty hospitals}">
    <table>
        <tr>
            <th>病院ID</th>
            <th>病院名</th>
            <th>住所</th>
            <th>電話番号</th>
            <th>資本金</th>
            <th>救急対応</th>
        </tr>
        <c:forEach var="hospital" items="${hospitals}">
            <tr>
                <td>${hospital.tabyouinid}</td>
                <td>${hospital.tabyouinmei}</td>
                <td>${hospital.tabyouinaddres}</td>
                <td>${hospital.tabyouintel}</td>
                <td>${hospital.tabyouinshihonkin}</td>
                <td>${hospital.kyukyu}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${empty hospitals}">
    <p>該当する病院がありません。</p>
</c:if>
</body>
</html>
