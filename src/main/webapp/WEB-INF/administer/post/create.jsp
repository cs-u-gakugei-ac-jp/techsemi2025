<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ブログ記事の作成</title>
    <link rel="stylesheet" href="/css/administer/post/create.css">
</head>
<body>
    <h1>ブログ記事の作成</h1>
    <form action="/administer/post/execute-create" method="post">
        <label for="title">タイトル</label>
        <input type="text" id="title" name="title" placeholder="タイトル" required>
        
        <label for="content">記事</label>
        <textarea id="content" name="text" placeholder="記事を入力" required></textarea>
        
        <button type="submit">作成</button>
    </form>
</body>
</html>