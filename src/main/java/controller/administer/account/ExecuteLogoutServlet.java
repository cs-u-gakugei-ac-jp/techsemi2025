package controller.administer.account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/administer/account/execute-logout")
public class ExecuteLogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // セッションを取得し破棄
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // ログイン画面にリダイレクト
        response.sendRedirect(request.getContextPath() + "/administer/account/login");
    }
}
