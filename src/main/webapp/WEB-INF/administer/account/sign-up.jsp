<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>アカウント登録</title>
    <link rel="stylesheet" href="/css/common/alert.css">
    <link rel="stylesheet" href="/css/administer/account/sign-up.css">
</head>
<body>
    <form action="/administer/account/execute-create" method="post">
        <h1>アカウント登録</h1>
        <%@ include file="/WEB-INF/common/alert.jsp" %>
        <label for="username">ユーザー名</label>
        <input type="text" id="username" name="username" placeholder="ユーザ名" required>
        <label for="password">パスワード</label>
        <input type="password" id="password" name="password" placeholder="パスワード" required>
        <button type="submit">アカウント登録</button>
    </form>
</body>
</html>
