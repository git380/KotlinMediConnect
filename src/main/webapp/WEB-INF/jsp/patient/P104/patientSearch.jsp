<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>保険証期限確認結果</title>
</head>
<body>
<h1>保険証期限確認結果</h1>
<h3>現在<br>${date}</h3>
<h2>有効期限が切れた患者</h2>
<table>
    <tr>
        <th>患者ID</th>
        <th>姓</th>
        <th>名</th>
        <th>保険証記号番号</th>
        <th>有効期限</th>
    </tr>
    <c:forEach var="patient" items="${expiredPatients}">
    <tr>
        <td>${patient.patid}</td>
        <td>${patient.patfname}</td>
        <td>${patient.patlname}</td>
        <td>${patient.hokenmei}</td>
        <td>${patient.hokenexp}</td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
