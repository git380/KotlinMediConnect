<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>従業員一覧・ID検索</title>
</head>
<body>
<h1>従業員一覧・ID検索</h1>
<input type="text" id="searchId">
<button onclick="search()">検索</button>
<button onclick="reset()">リセット</button>
<table>
    <tr>
        <th>従業員ID</th>
        <th>姓</th>
        <th>名</th>
        <th>変更</th>
    </tr>
    <c:forEach var="employee" items="${employeeList}">
        <tr>
            <td>${employee.empId}</td>
            <td>${employee.empLname}</td>
            <td>${employee.empFname}</td>
            <td>
                <form action="EmployeeSearchServlet" method="post">
                    <input type="hidden" name="empId" value="${employee.empId}">
                    <input type="submit" value="変更">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<script>
    function search() {
        const input = document.getElementById("searchId").value;
        const table = document.getElementsByTagName("table")[0];
        const rows = table.getElementsByTagName("tr");
        const length = rows.length;
        let matchFound = false;

        for (var i = 0; i < length; i++) {
            var cell = rows[i].getElementsByTagName("td")[0];
            if (cell) {
                if (cell.innerText === input) {
                    matchFound = true;
                    break;// 該当した場合、ループを抜ける
                }
            }
        }

        if (matchFound) {// 該当した場合
            for (var i = 0; i < length; i++) {
                var cell = rows[i].getElementsByTagName("td")[0];
                if (cell) {
                    if (cell.innerText === input) {
                        rows[i].style.display = "";
                    } else {
                        rows[i].style.display = "none";
                    }
                }
            }
        } else {
            alert("該当する従業員IDが見つかりませんでした。");
        }
    }

    function reset() {
        const table = document.getElementsByTagName("table")[0];
        const rows = table.getElementsByTagName("tr");

        for (var i = 0; i < rows.length; i++) {
            rows[i].style.display = "";
        }

        document.getElementById("searchId").value = "";
    }
</script>
</body>
</html>
