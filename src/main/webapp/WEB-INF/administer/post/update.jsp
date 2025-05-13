<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ブログ記事の更新</title>
    <link rel="stylesheet" href="/css/administer/post/update.css">
</head>
<body>
    <h1>ブログ記事の更新</h1>
    <form action="/administer/post/execute-update" method="post">
        <input type="hidden" name="postId" value="${post.postId}">
        <label for="title">タイトル</label>
        <input type="text" id="title" name="title" placeholder="タイトル" value="${post.postTitle}" required>
        
        <label for="content">記事</label>
        <textarea id="content" name="text" placeholder="記事を入力" required>${post.postText}</textarea>
        
        <button type="submit">更新</button>
    </form>
    <c:if test="${not empty error}">
        <div class="error">
            <p>${error}</p>
        </div>
    </c:if>
</body>