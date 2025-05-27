<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MiyaBlog</title>
    <link rel="stylesheet" href="/css/common/alert.css">
    <link rel="stylesheet" href="/css/reader/home.css">
</head>
<body>
    <%@ include file="/WEB-INF/common/alert.jsp" %>
    <h1>MiyaBlog</h1>
    <p>最新の研究をお伝えします</p>
    <c:if test="${not empty posts}">
        <div class="posts">
            <c:forEach var="post" items="${posts}">
                <div class="post">
                    <h2>${post.postTitle}</h2>
                    <p>${post.postText}</p>
                    <p>投稿日時: ${post.createdAt}</p>
                    <button onclick="location.href='/detail?postId=${post.postId}'">投稿を見る</button>
                </div>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${empty posts}">
        <p>投稿データがありません。</p>
    </c:if>
</body>
</html>
