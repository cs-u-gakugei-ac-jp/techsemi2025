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

@WebServlet("/administer/post/update")
public class UpdateServlet extends HttpServlet {
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
                Post post = postsDao.getOne(postId);
                if (post != null) {
                    request.setAttribute("post", post);
                } else {
                    request.setAttribute("error", "指定された投稿が見つかりませんでした。");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "無効な投稿IDが指定されました。");
            } catch (Exception e) {
                request.setAttribute("error", "投稿情報の取得中にエラーが発生しました。");
            }
        } else {
            request.setAttribute("error", "投稿IDが指定されていません。");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/administer/post/update.jsp");
        dispatcher.forward(request, response);
    }
}
