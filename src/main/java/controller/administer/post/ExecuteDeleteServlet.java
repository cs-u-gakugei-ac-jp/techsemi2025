package controller.administer.post;

import java.io.IOException; // IOExceptionのインポート
import java.net.URLEncoder; // URLEncoderのインポート
import java.nio.charset.StandardCharsets; // StandardCharsetsのインポート
import javax.servlet.ServletException; // サーブレット例外のインポート
import javax.servlet.annotation.WebServlet; // WebServletアノテーションのインポート
import javax.servlet.http.HttpServlet; // HttpServletクラスのインポート
import javax.servlet.http.HttpServletRequest; // HttpServletRequestのインポート
import javax.servlet.http.HttpServletResponse; // HttpServletResponseのインポート
import javax.servlet.http.HttpSession; // HttpSessionのインポート

import dao.PostsDao; // PostsDaoのインポート
import dao.DaoException; // DaoExceptionのインポート

@WebServlet("/administer/post/execute-delete") // このサーブレットのURLマッピング
public class ExecuteDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // 既存のセッションを取得（なければnull）
        if (session == null || session.getAttribute("userId") == null) { // セッションまたはuserId属性がなければ
            response.sendRedirect(request.getContextPath() + "/administer/account/login"); // ログイン画面へリダイレクト
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
                    String error = URLEncoder.encode("削除に失敗しました", StandardCharsets.UTF_8.toString());
                    response.sendRedirect("/administer/post/home?error=" + error);
                }
            } catch (NumberFormatException e) {
                String error = URLEncoder.encode("無効な投稿IDです", StandardCharsets.UTF_8.toString());
                response.sendRedirect("/administer/post/home?error=" + error);
            }
        } else {
            String error = URLEncoder.encode("投稿IDが指定されていません", StandardCharsets.UTF_8.toString());
            response.sendRedirect("/administer/post/home?error=" + error);
        }
    }
}
