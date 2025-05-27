<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>投稿詳細</title>
    <link rel="stylesheet" href="/css/common/alert.css">
    <link rel="stylesheet" href="/css/reader/detail.css">
</head>
<body>
    <%@ include file="/WEB-INF/common/alert.jsp" %>
    <h1>投稿詳細</h1>
    <c:if test="${not empty post}">
        <div class="post-detail">
            <h2>${post.postTitle}</h2>
            <p>${post.postText}</p>
            <p>投稿日時: ${post.createdAt}</p>
            <c:if test="${not empty post.updatedAt}">
                <p>更新日時: ${post.updatedAt}</p>
            </c:if>
        </div>
    </c:if>
    <c:if test="${empty post}">
        <p>投稿が見つかりません。</p>
    </c:if>
    <button onclick="location.href='/'">戻る</button>
</body>
</html>