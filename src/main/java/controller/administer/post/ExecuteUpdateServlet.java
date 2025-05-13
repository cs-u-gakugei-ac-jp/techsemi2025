package controller.administer.post;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PostsDao;
import entity.Post;
import java.time.LocalDateTime;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/administer/post/execute-update")
public class ExecuteUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/administer/account/login");
            return;
        }

        try {
            int postId = Integer.parseInt(request.getParameter("postId"));
            String title = request.getParameter("title");
            String text = request.getParameter("text");

            PostsDao postsDao = new PostsDao();
            Post post = postsDao.getOne(postId);

            if (post != null) {
                post.setPostTitle(title);
                post.setPostText(text);
                post.setUpdatedAt(LocalDateTime.now());

                postsDao.update(post);
                String successMessage = URLEncoder.encode("投稿の更新が完了しました。", StandardCharsets.UTF_8.toString());
                response.sendRedirect("/administer/post/home?message=" + successMessage);
            } else {
                String errorMessage = URLEncoder.encode("指定された投稿が見つかりませんでした。", StandardCharsets.UTF_8.toString());
                response.sendRedirect("/administer/post/home?error=" + errorMessage);
            }
        } catch (Exception e) {
            String exceptionMessage = URLEncoder.encode("投稿情報の更新中にエラーが発生しました。", StandardCharsets.UTF_8.toString());
            response.sendRedirect("/administer/post/home?error=" + exceptionMessage);
        }
    }
}
