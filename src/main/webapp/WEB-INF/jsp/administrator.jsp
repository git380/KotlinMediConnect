<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理者画面</title>
</head>
<body>
<h1>ようこそ!管理者画面です。</h1>
<ul>
    <li><a href="LogoutServlet">ログアウト</a></li>
    <li><a href="PwChangeServlet">自分のパスワードの変更</a></li>
    <li><a href="RegisterServlet">従業員登録</a></li>
    <li><a href="EmployeeSearchServlet">従業員氏名変更(従業員一覧・ID検索)</a></li>
    <li><a href="HospitalRegistrationServlet">他病院登録</a></li>
    <li><a href="HospitalSearchServlet">他病院住所検索</a></li>
</ul>
</body>
</html>