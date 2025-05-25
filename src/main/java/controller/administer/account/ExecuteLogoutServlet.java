package controller.administer.account;

import javax.servlet.ServletException; // サーブレット例外のインポート
import javax.servlet.annotation.WebServlet; // WebServletアノテーションのインポート
import javax.servlet.http.HttpServlet; // HttpServletクラスのインポート
import javax.servlet.http.HttpServletRequest; // HttpServletRequestのインポート
import javax.servlet.http.HttpServletResponse; // HttpServletResponseのインポート
import javax.servlet.http.HttpSession; // HttpSessionのインポート
import java.io.IOException; // IOExceptionのインポート

@WebServlet("/administer/account/execute-logout") // このサーブレットのURLマッピング
public class ExecuteLogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // セッションを取得し破棄
        HttpSession session = request.getSession(false); // 既存のセッションを取得（なければnull）
        if (session != null) {
            session.invalidate(); // セッションが存在すれば破棄
        }

        // ログイン画面にリダイレクト
        response.sendRedirect(request.getContextPath() + "/administer/account/login"); // ログイン画面へリダイレクト
    }
}
