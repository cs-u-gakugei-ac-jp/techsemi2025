package controller.administer.post;

import java.io.IOException; // IOExceptionのインポート
import java.net.URLEncoder;
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

        // messageとerrorの取得・セット
        String message = request.getParameter("message");
        if (message != null && !message.isEmpty()) {
            request.setAttribute("message", message);
        }
        String error = request.getParameter("error");
        if (error != null && !error.isEmpty()) {
            request.setAttribute("error", error);
        }

        HttpSession session = request.getSession(false); // 既存のセッションを取得（なければnull）
        if (session == null || session.getAttribute("userId") == null) { // セッションまたはuserId属性がなければ
            String errorMessage = URLEncoder.encode("ログインしてください", "UTF-8");
            response.sendRedirect(request.getContextPath()
                    + "/administer/account/login?error=" + errorMessage); // ログイン画面へリダイレクト（エラー付き）
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/administer/post/create.jsp"); // 作成画面のJSPへのディスパッチャ取得
        dispatcher.forward(request, response); // JSPへフォワード
    }
}