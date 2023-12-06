<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>他病院登録</title>
</head>
<body>
<h1>他病院登録</h1>
<form action="HospitalRegistrationServlet" method="post">
    <label for="tabyouinid">他病院ID:</label>
    <input type="text" id="tabyouinid" name="tabyouinid"><br>
    <label for="tabyouinmei">他病院名:</label>
    <input type="text" id="tabyouinmei" name="tabyouinmei"><br>
    <label for="tabyouinaddres">他病院住所:</label>
    <input type="text" id="tabyouinaddres" name="tabyouinaddres"><br>
    <label>他病院電話番号:</label>
    <input type="text" id="tabyouintel1" name="tabyouintel1" pattern="[\d]+" minlength="3" maxlength="4">-
    <input type="text" id="tabyouintel2" name="tabyouintel2" pattern="[\d]+" minlength="3" maxlength="4">-
    <input type="text" id="tabyouintel3" name="tabyouintel3" pattern="[\d]+" minlength="3" maxlength="4"><br>
    <input type="text" name="tabyouintel" value="" id="tabyouintel-hidden"><br>
    <label for="tabyouinshihonkin">資本金:</label>
    <input type="number" id="tabyouinshihonkin" name="tabyouinshihonkin" min="0"><br>
    <label for="kyukyu">救急対応:</label>
    <select id="kyukyu" name="kyukyu">
        <option value="0">なし</option>
        <option value="1">あり</option>
    </select><br>
    <input type="submit" value="登録" disabled>
</form>

<script>
    const inputs = document.querySelectorAll('input[type=text], input[type=number]');
    const submitButton = document.querySelector('input[type=submit]');
    Array.from(inputs).forEach(input => {
        input.addEventListener('input', () => {
            submitButton.disabled = Array.from(inputs).some(input => !input.value);
        });
    });

    const tabyouintel1 = document.getElementById('tabyouintel1');
    const tabyouintel2 = document.getElementById('tabyouintel2');
    const tabyouintel3 = document.getElementById('tabyouintel3');

    const tabyouintelHidden = document.getElementById('tabyouintel-hidden');

    function updateHidden() {
        tabyouintelHidden.value = tabyouintel1.value + '-' + tabyouintel2.value + '-' + tabyouintel3.value;
    }

    tabyouintel1.addEventListener('input', updateHidden);
    tabyouintel2.addEventListener('input', updateHidden);
    tabyouintel3.addEventListener('input', updateHidden);
</script>
</body>
</html>