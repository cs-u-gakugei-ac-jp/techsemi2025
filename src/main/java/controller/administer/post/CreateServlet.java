package controller.administer.post;

import java.io.IOException; // IOExceptionのインポート
import javax.servlet.RequestDispatcher; // RequestDispatcherのインポート
import javax.servlet.ServletException; // サーブレット例外のインポート
import javax.servlet.annotation.WebServlet; // WebServletアノテーションのインポート
import javax.servlet.http.HttpServlet; // HttpServletクラスのインポート
import javax.servlet.http.HttpServletRequest; // HttpServletRequestのインポート
import javax.servlet.http.HttpServletResponse; // HttpServletResponseのインポート
import javax.servlet.http.HttpSession; // HttpSessionのインポート

@WebServlet("/administer/post/create") // このサーブレットのURLマッピング
public class CreateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // 既存のセッションを取得（なければnull）
        if (session == null || session.getAttribute("userId") == null) { // セッションまたはuserId属性がなければ
            response.sendRedirect(request.getContextPath() + "/administer/account/login"); // ログイン画面へリダイレクト
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/administer/post/create.jsp"); // 作成画面のJSPへのディスパッチャ取得
        dispatcher.forward(request, response); // JSPへフォワード
    }
}