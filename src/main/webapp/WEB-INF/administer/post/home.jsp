<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MiyaBlog</title>
    <link rel="stylesheet" href="/css/common/alert.css">
    <link rel="stylesheet" href="/css/administer/post/home.css">
    <script>
        function confirmDelete(postId) {
            if (confirm("投稿を削除してよろしいですか？")) {
                location.href = '/administer/post/execute-delete?postId=' + encodeURIComponent(postId);
            }
        }

        function confirmLogout() {
            if (confirm("ログアウトしてよろしいですか？")) {
                location.href = '/administer/account/execute-logout';
            }
        }
    </script>
</head>
<body>
    <h1>MiyaBlog</h1>
    <%@ include file="/WEB-INF/common/alert.jsp" %>
    <p>最新の研究をお伝えします</p>
    <button class="new-post" onclick="location.href='/administer/post/create'">新規作成画面</button>
    <button class="logout" onclick="confirmLogout()">ログアウト</button>
    <c:if test="${not empty posts}">
        <div class="posts">
            <c:forEach var="post" items="${posts}">
                <div class="post">
                    <h2>${post.postTitle}</h2>
                    <p>${post.postText}</p>
                    <p>投稿日時: ${post.createdAt}</p>
                    <button class="update" onclick="location.href='/administer/post/update?postId=${post.postId}'">更新</button>
                    <button class="delete" onclick="confirmDelete(${post.postId})">削除</button>
                </div>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${empty posts}">
        <p>投稿データがありません。</p>
    </c:if>

</body>
</html>
