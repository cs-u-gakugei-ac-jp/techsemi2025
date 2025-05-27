package controller.reader;

import dao.PostsDao; // PostsDaoのインポート
import entity.Post; // Postエンティティのインポート
import javax.servlet.RequestDispatcher; // RequestDispatcherのインポート
import javax.servlet.ServletException; // サーブレット例外のインポート
import javax.servlet.annotation.WebServlet; // WebServletアノテーションのインポート
import javax.servlet.http.HttpServlet; // HttpServletクラスのインポート
import javax.servlet.http.HttpServletRequest; // HttpServletRequestのインポート
import javax.servlet.http.HttpServletResponse; // HttpServletResponseのインポート
import java.io.IOException; // IOExceptionのインポート

@WebServlet("/detail") // このサーブレットのURLマッピング
public class DtailServlet extends HttpServlet {

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

        String postIdParam = request.getParameter("postId"); // リクエストから投稿IDを取得
        if (postIdParam == null || postIdParam.isEmpty()) { // 投稿IDが指定されていない場合
            String err = "投稿IDが指定されていません。"; // エラーメッセージをセット
            request.setAttribute("error", err); // リクエスト属性にエラーをセット
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reader/detail.jsp"); // 詳細画面のJSPへのディスパッチャ取得
            dispatcher.forward(request, response); // JSPへフォワード
            return;
        }

        try {
            int postId = Integer.parseInt(postIdParam);
            PostsDao postsDao = new PostsDao();
            Post post = postsDao.getOne(postId);

            if (post == null) {
                String err = "投稿が見つかりません。";
                request.setAttribute("error", err);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reader/detail.jsp");
                dispatcher.forward(request, response);
                return;
            }

            request.setAttribute("post", post);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reader/detail.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            String err = "投稿IDの形式が不正です。";
            request.setAttribute("error", err);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reader/detail.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            String err = "投稿詳細の取得中にエラーが発生しました。";
            request.setAttribute("error", err);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reader/detail.jsp");
            dispatcher.forward(request, response);
        }
    }
}
