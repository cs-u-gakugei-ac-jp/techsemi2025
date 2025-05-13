package controller.reader;

import dao.PostsDao;
import entity.Post;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/detail")
public class DtailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postIdParam = request.getParameter("postId");
        if (postIdParam == null || postIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "postId is required");
            return;
        }

        try {
            int postId = Integer.parseInt(postIdParam);
            PostsDao postsDao = new PostsDao();
            Post post = postsDao.getOne(postId);

            if (post == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Post not found");
                return;
            }

            request.setAttribute("post", post);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reader/detail.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid postId format");
        } catch (Exception e) {
            throw new ServletException("Error retrieving post details", e);
        }
    }
}
