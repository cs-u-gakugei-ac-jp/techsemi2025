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
        // messageとerrorの取得・セット
        String paramMessage = request.getParameter("message");
        if (paramMessage != null && !paramMessage.isEmpty()) {
            request.setAttribute("message", paramMessage);
        }
        String paramError = request.getParameter("error");
        if (paramError != null && !paramError.isEmpty()) {
            request.setAttribute("error", paramError);
        }

        HttpSession session = request.getSession(false); // 既存のセッションを取得（なければnull）
        if (session == null || session.getAttribute("userId") == null) { // セッションまたはuserId属性がなければ
            String errorMessage = java.net.URLEncoder.encode("ログインしてください", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/administer/account/login?error=" + errorMessage); // ログイン画面へリダイレクト（エラー付き）
            return;
        }

        String postIdParam = request.getParameter("postId");
        if (postIdParam != null) {
            try {
                int postId = Integer.parseInt(postIdParam);
                PostsDao postsDao = new PostsDao();
                try {
                    postsDao.delete(postId);
                    String msg = URLEncoder.encode("削除が成功しました", StandardCharsets.UTF_8.toString());
                    response.sendRedirect("/administer/post/home?message=" + msg);
                } catch (DaoException e) {
                    String err = URLEncoder.encode("削除に失敗しました", StandardCharsets.UTF_8.toString());
                    response.sendRedirect("/administer/post/home?error=" + err);
                }
            } catch (NumberFormatException e) {
                String err = URLEncoder.encode("無効な投稿IDです", StandardCharsets.UTF_8.toString());
                response.sendRedirect("/administer/post/home?error=" + err);
            }
        } else {
            String err = URLEncoder.encode("投稿IDが指定されていません", StandardCharsets.UTF_8.toString());
            response.sendRedirect("/administer/post/home?error=" + err);
        }
    }
}
