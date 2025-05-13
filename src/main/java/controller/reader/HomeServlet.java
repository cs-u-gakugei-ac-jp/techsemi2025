package controller.reader;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.PostsDao;
import entity.Post;

import java.util.ArrayList;
import java.util.List;

@WebServlet("")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reader/home.jsp");
        dispatcher.forward(request, response);
    }
}