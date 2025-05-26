package controller.administer.account;

import java.io.IOException;
import java.net.URLEncoder; // URLエンコード用
import java.nio.charset.StandardCharsets; // 文字エンコーディング指定用
import java.security.MessageDigest; // パスワードハッシュ化用
import java.security.NoSuchAlgorithmException; // ハッシュアルゴリズム例外用
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet; // サーブレットアノテーション
import javax.servlet.http.HttpServlet; // HttpServlet継承
import javax.servlet.http.HttpServletRequest; // リクエスト取得用
import javax.servlet.http.HttpServletResponse; // レスポンス返却用

import dao.UsersDao; // ユーザーDAO
import dao.DaoException; // DAO例外
import entity.User; // ユーザーエンティティ
import modelUtil.Failure; // 独自例外

@WebServlet("/administer/account/execute-create") // サーブレットのURLマッピング
public class ExecuteCreateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // フォームデータの取得
        String username = request.getParameter("username"); // ユーザー名取得
        String password = request.getParameter("password"); // パスワード取得

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            try {
                // ハッシュ化されたパスワードを生成
                String hashedPassword = hashPassword(password);

                // ユーザー登録処理
                UsersDao usersDao = new UsersDao(); // DAOインスタンス生成
                User user = new User(0, username, hashedPassword); // ユーザーエンティティ生成（IDは自動採番想定）
                usersDao.create(user); // DBにユーザー登録

                // メッセージをエンコードしてリダイレクト
                String message = URLEncoder.encode("アカウント登録が完了しました。", StandardCharsets.UTF_8.toString());
                response.sendRedirect(request.getContextPath() + "/administer/account/login?message=" + message); // ログイン画面へリダイレクト
            } catch (Failure | DaoException e) {
                // 登録失敗時のエラー処理
                String error = URLEncoder.encode("アカウント登録中にエラーが発生しました: " + e.getMessage(),
                        StandardCharsets.UTF_8.toString());
                response.sendRedirect(request.getContextPath() + "/administer/account/sign-up?error=" + error); // サインアップ画面へリダイレクト
            }
        } else {
            // 入力値が不足している場合のエラー処理
            String error = URLEncoder.encode("ユーザー名とパスワードを入力してください。", StandardCharsets.UTF_8.toString());
            response.sendRedirect(request.getContextPath() + "/administer/account/sign-up?error=" + error); // サインアップ画面へリダイレクト
        }
    }

    // パスワードをSHA-256でハッシュ化するメソッド
    private String hashPassword(String password) throws Failure {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // SHA-256インスタンス取得
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8)); // パスワードをバイト配列に変換しハッシュ化
            StringBuilder hexString = new StringBuilder(); // 16進数文字列用
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b); // 1バイトずつ16進数に変換
                if (hex.length() == 1) {
                    hexString.append('0'); // 1桁の場合は0埋め
                }
                hexString.append(hex);
            }
            return hexString.toString(); // ハッシュ値を返却
        } catch (NoSuchAlgorithmException e) {
            throw new Failure("パスワードのハッシュ化に失敗しました。", e); // ハッシュ化失敗時の例外
        }
    }
}
