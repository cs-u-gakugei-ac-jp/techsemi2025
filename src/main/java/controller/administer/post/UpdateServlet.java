package controller.administer.post;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException; // サーブレット例外のインポート
import javax.servlet.annotation.WebServlet; // WebServletアノテーションのインポート
import javax.servlet.http.HttpServlet; // HttpServletクラスのインポート
import javax.servlet.http.HttpServletRequest; // HttpServletRequestのインポート
import javax.servlet.http.HttpServletResponse; // HttpServletResponseのインポート
import javax.servlet.http.HttpSession; // HttpSessionのインポート

import dao.PostsDao; // PostsDaoのインポート
import entity.Post; // Postエンティティのインポート

@WebServlet("/administer/post/update") // このサーブレットを /administer/post/update でマッピング
public class UpdateServlet extends HttpServlet {
    // GETリクエストを処理するメソッド
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // セッションを取得（なければnull）
        if (session == null || session.getAttribute("userId") == null) { // 未ログインの場合
            response.sendRedirect(request.getContextPath() + "/administer/account/login"); // ログイン画面へリダイレクト
            return;
        }

        String postIdParam = request.getParameter("postId"); // リクエストパラメータから投稿IDを取得
        if (postIdParam != null) {
            try {
                int postId = Integer.parseInt(postIdParam); // 投稿IDをint型に変換
                PostsDao postsDao = new PostsDao(); // 投稿DAOのインスタンス生成
                Post post = postsDao.getOne(postId); // 指定IDの投稿を取得
                if (post != null) {
                    request.setAttribute("post", post); // 投稿情報をリクエスト属性にセット
                } else {
                    request.setAttribute("error", "指定された投稿が見つかりませんでした。"); // 投稿が見つからない場合のエラー
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "無効な投稿IDが指定されました。"); // 投稿IDが数値でない場合のエラー
            } catch (Exception e) {
                request.setAttribute("error", "投稿情報の取得中にエラーが発生しました。"); // その他の例外時のエラー
            }
        } else {
            request.setAttribute("error", "投稿IDが指定されていません。"); // 投稿IDが指定されていない場合のエラー
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/administer/post/update.jsp"); // JSPへのディスパッチャ取得
        dispatcher.forward(request, response); // JSPへフォワード
    }
}
