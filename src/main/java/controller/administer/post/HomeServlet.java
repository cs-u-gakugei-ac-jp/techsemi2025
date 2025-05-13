package controller.administer.post;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.PostsDao;
import entity.Post;

import java.util.ArrayList;
import java.util.List;

@WebServlet("/administer/post/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/administer/account/login");
            return;
        }

        try {
            PostsDao postsDao = new PostsDao();
            List<Post> posts = postsDao.getAll();
            if (posts == null) {
                posts = new ArrayList<>(); // 空のリストを代入
            }
            request.setAttribute("posts", posts);
        } catch (Exception e) {
            throw new ServletException("投稿データの取得中にエラーが発生しました。", e);
        }
        String message = request.getParameter("message");
        if (message != null && !message.isEmpty()) {
            request.setAttribute("alertMessage", message);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/administer/post/home.jsp");
        dispatcher.forward(request, response);
    }
}