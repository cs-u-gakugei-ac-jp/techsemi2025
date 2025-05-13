package controller.administer.account;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/administer/account/login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // メッセージの取得
        String message = request.getParameter("message");
        if (message != null && !message.isEmpty()) {
            request.setAttribute("message", message);
        }

        // ログイン画面へのフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/administer/account/login.jsp");
        dispatcher.forward(request, response);
    }
}
