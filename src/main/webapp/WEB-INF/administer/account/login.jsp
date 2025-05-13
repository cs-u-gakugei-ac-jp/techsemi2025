<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ログイン</title>
    <link rel="stylesheet" href="/css/administer/account/login.css">
</head>
<body>
    <form action="/administer/account/execute-login" method="post">
        <h1>ログイン</h1>
        <c:if test="${not empty message}">
            <p class="message">${message}</p>
        </c:if>
        <label for="username">ユーザー名</label>
        <input type="text" id="username" name="username" placeholder="ユーザ名" required>
        <label for="password">パスワード</label>
        <input type="password" id="password" name="password" placeholder="パスワード" required>
        <button type="submit">ログイン</button>
    </form>
</body>
</html>
