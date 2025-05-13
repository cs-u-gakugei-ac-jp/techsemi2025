package controller.administer.account;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsersDao;
import dao.DaoException;
import entity.User;
import modelUtil.Failure;

@WebServlet("/administer/account/execute-create")
public class ExecuteCreateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // フォームデータの取得
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            try {
                // ハッシュ化されたパスワードを生成
                String hashedPassword = hashPassword(password);

                // ユーザー登録処理
                UsersDao usersDao = new UsersDao();
                User user = new User(0, username, hashedPassword); // ハッシュ化されたパスワードを使用
                usersDao.create(user);

                // メッセージをエンコードしてリダイレクト
                String message = URLEncoder.encode("アカウント登録が完了しました。", StandardCharsets.UTF_8.toString());
                response.sendRedirect(request.getContextPath() + "/administer/account/login?message=" + message);
            } catch (Failure | DaoException e) {
                // エラー時のリダイレクト
                String errorMessage = URLEncoder.encode("アカウント登録中にエラーが発生しました: " + e.getMessage(),
                        StandardCharsets.UTF_8.toString());
                response.sendRedirect(
                        request.getContextPath() + "/administer/account/sign-up?errorMessage=" + errorMessage);
            }
        } else {
            // エラー時のリダイレクト
            String errorMessage = URLEncoder.encode("ユーザー名とパスワードを入力してください。", StandardCharsets.UTF_8.toString());
            response.sendRedirect(
                    request.getContextPath() + "/administer/account/sign-up?errorMessage=" + errorMessage);
        }
    }

    private String hashPassword(String password) throws Failure {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new Failure("パスワードのハッシュ化に失敗しました。", e);
        }
    }
}
