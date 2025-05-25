package controller.administer.post;

import java.io.IOException; // IOExceptionのインポート
import javax.servlet.ServletException; // サーブレット例外のインポート
import javax.servlet.annotation.WebServlet; // WebServletアノテーションのインポート
import javax.servlet.http.HttpServlet; // HttpServletクラスのインポート
import javax.servlet.http.HttpServletRequest; // HttpServletRequestのインポート
import javax.servlet.http.HttpServletResponse; // HttpServletResponseのインポート
import javax.servlet.http.HttpSession; // HttpSessionのインポート

import dao.PostsDao; // PostsDaoのインポート
import entity.Post; // Postエンティティのインポート
import java.time.LocalDateTime; // LocalDateTimeのインポート
import java.net.URLEncoder; // URLEncoderのインポート
import java.nio.charset.StandardCharsets; // StandardCharsetsのインポート

@WebServlet("/administer/post/execute-update") // このサーブレットのURLマッピング
public class ExecuteUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // 既存のセッションを取得（なければnull）
        if (session == null || session.getAttribute("userId") == null) { // セッションまたはuserId属性がなければ
            response.sendRedirect(request.getContextPath() + "/administer/account/login"); // ログイン画面へリダイレクト
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
                String error = URLEncoder.encode("指定された投稿が見つかりませんでした。", StandardCharsets.UTF_8.toString());
                response.sendRedirect("/administer/post/home?error=" + error);
            }
        } catch (Exception e) {
            String error = URLEncoder.encode("投稿情報の更新中にエラーが発生しました。", StandardCharsets.UTF_8.toString());
            response.sendRedirect("/administer/post/home?error=" + error);
        }
    }
}
