package controller.reader;

import java.io.IOException; // IOExceptionのインポート
import javax.servlet.RequestDispatcher; // RequestDispatcherのインポート
import javax.servlet.ServletException; // サーブレット例外のインポート
import javax.servlet.annotation.WebServlet; // WebServletアノテーションのインポート
import javax.servlet.http.HttpServlet; // HttpServletクラスのインポート
import javax.servlet.http.HttpServletRequest; // HttpServletRequestのインポート
import javax.servlet.http.HttpServletResponse; // HttpServletResponseのインポート
import dao.PostsDao; // PostsDaoのインポート
import entity.Post; // Postエンティティのインポート

import java.util.ArrayList; // ArrayListのインポート
import java.util.List; // Listのインポート

@WebServlet("") // このサーブレットのURLマッピング（ルート）
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // messageとerrorの取得・セット
        String message = request.getParameter("message");
        if (message != null && !message.isEmpty()) {
            request.setAttribute("message", message);
        }
        String error = request.getParameter("error");
        if (error != null && !error.isEmpty()) {
            request.setAttribute("error", error);
        }

        try {
            PostsDao postsDao = new PostsDao(); // PostsDaoのインスタンス生成
            List<Post> posts = postsDao.getAll(); // 全投稿データを取得
            if (posts == null) {
                posts = new ArrayList<>(); // nullの場合は空リストをセット
            }
            request.setAttribute("posts", posts); // 投稿リストをリクエスト属性にセット
        } catch (Exception e) {
            request.setAttribute("error", "投稿データの取得中にエラーが発生しました。: " + e.getMessage());
        }

        String msg = request.getParameter("message");
        if (msg != null && !msg.isEmpty()) {
            request.setAttribute("message", msg);
        }
        String err = request.getParameter("error");
        if (err != null && !err.isEmpty()) {
            request.setAttribute("error", err);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reader/home.jsp");
        dispatcher.forward(request, response);
    }
}