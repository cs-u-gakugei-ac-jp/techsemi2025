package controller.administer.account;

import java.io.IOException; // IOExceptionのインポート
import javax.servlet.RequestDispatcher; // RequestDispatcherのインポート
import javax.servlet.ServletException; // サーブレット例外のインポート
import javax.servlet.annotation.WebServlet; // WebServletアノテーションのインポート
import javax.servlet.http.HttpServlet; // HttpServletクラスのインポート
import javax.servlet.http.HttpServletRequest; // HttpServletRequestのインポート
import javax.servlet.http.HttpServletResponse; // HttpServletResponseのインポート

@WebServlet("/administer/account/login") // このサーブレットのURLマッピング
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // messageとerrorの取得・セット
        String message = request.getParameter("message"); // リクエストパラメータから"message"を取得
        if (message != null && !message.isEmpty()) { // messageが存在し空でない場合
            request.setAttribute("message", message); // リクエスト属性に"message"をセット
        }
        String error = request.getParameter("error"); // リクエストパラメータから"error"を取得
        if (error != null && !error.isEmpty()) { // errorが存在し空でない場合
            request.setAttribute("error", error); // リクエスト属性に"error"をセット
        }

        // ログイン画面へのフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/administer/account/login.jsp"); // ログイン画面のJSPへのディスパッチャ取得
        dispatcher.forward(request, response); // JSPへフォワード
    }
}
