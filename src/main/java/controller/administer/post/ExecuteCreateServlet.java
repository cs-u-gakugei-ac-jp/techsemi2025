package controller.administer.post;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Post;
import dao.PostsDao;
import dao.DaoException;
import java.time.LocalDateTime;

@WebServlet("/administer/post/execute-create")
public class ExecuteCreateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/administer/account/login");
            return;
        }

        String title = request.getParameter("title");
        String text = request.getParameter("text");
        int userId = (Integer) session.getAttribute("userId");

        Post post = new Post(0, LocalDateTime.now(), null, title, text, userId);
        PostsDao postsDao = new PostsDao();

        try {
            postsDao.create(post);
            response.sendRedirect(request.getContextPath() + "/administer/post/home?message=" +
                    java.net.URLEncoder.encode("投稿の作成が完了しました。", "UTF-8"));
            return;
        } catch (DaoException e) {
            throw new ServletException("投稿の作成中にエラーが発生しました。", e);
        }
    }
}