package controller.administer.post;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PostsDao;
import dao.DaoException;

@WebServlet("/administer/post/execute-delete")
public class ExecuteDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/administer/account/login");
            return;
        }

        String postIdParam = request.getParameter("postId");
        if (postIdParam != null) {
            try {
                int postId = Integer.parseInt(postIdParam);
                PostsDao postsDao = new PostsDao();
                try {
                    postsDao.delete(postId);
                    String message = URLEncoder.encode("削除が成功しました", StandardCharsets.UTF_8.toString());
                    response.sendRedirect("/administer/post/home?message=" + message);
                } catch (DaoException e) {
                    String message = URLEncoder.encode("削除に失敗しました", StandardCharsets.UTF_8.toString());
                    response.sendRedirect("/administer/post/home?message=" + message);
                }
            } catch (NumberFormatException e) {
                String message = URLEncoder.encode("無効な投稿IDです", StandardCharsets.UTF_8.toString());
                response.sendRedirect("/administer/post/home?message=" + message);
            }
        } else {
            String message = URLEncoder.encode("投稿IDが指定されていません", StandardCharsets.UTF_8.toString());
            response.sendRedirect("/administer/post/home?message=" + message);
        }
    }
}
