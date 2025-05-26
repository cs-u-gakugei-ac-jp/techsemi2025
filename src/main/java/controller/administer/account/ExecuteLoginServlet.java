package controller.administer.account;

import dao.UsersDao; // UsersDaoクラスのインポート
import entity.User; // Userエンティティのインポート
import modelUtil.Failure; // Failure例外クラスのインポート
import javax.servlet.ServletException; // サーブレット例外のインポート
import javax.servlet.annotation.WebServlet; // WebServletアノテーションのインポート
import javax.servlet.http.HttpServlet; // HttpServletクラスのインポート
import javax.servlet.http.HttpServletRequest; // HttpServletRequestのインポート
import javax.servlet.http.HttpServletResponse; // HttpServletResponseのインポート
import javax.servlet.http.HttpSession; // HttpSessionのインポート
import java.io.IOException; // IOExceptionのインポート
import java.util.logging.Logger; // Loggerのインポート

@WebServlet("/administer/account/execute-login") // このサーブレットのURLマッピング
public class ExecuteLoginServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ExecuteLoginServlet.class.getName()); // ロガーの初期化

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("username"); // リクエストからユーザー名を取得
        String password = request.getParameter("password"); // リクエストからパスワードを取得

        logger.info("Received login request for userName: " + userName); // ログインリクエストをログ出力

        UsersDao usersDao = new UsersDao(); // UsersDaoのインスタンス生成
        try {
            User user = usersDao.getOneByUserName(userName); // ユーザー名でユーザー情報を取得
            if (user == null) { // ユーザーが存在しない場合
                logger.warning("User not found: " + userName); // ユーザーが見つからない旨をログ出力
                String error = java.net.URLEncoder.encode("ユーザーが見つかりません。", "UTF-8"); // エラーメッセージをエンコード
                response.sendRedirect("/administer/account/login?error=" + error); // ログイン画面にリダイレクト
                return;
            }

            String hashedPassword = hashPassword(password); // 入力パスワードをハッシュ化
            logger.info("Hashed input password: " + hashedPassword); // ハッシュ化したパスワードをログ出力
            logger.info("Stored password: " + user.getPassword()); // DBに保存されているパスワードをログ出力

            if (!hashedPassword.equals(user.getPassword())) { // パスワードが一致しない場合
                logger.warning("Password mismatch for user: " + userName); // パスワード不一致をログ出力
                String error = java.net.URLEncoder.encode("パスワードが一致しません。", "UTF-8"); // エラーメッセージをエンコード
                response.sendRedirect("/administer/account/login?error=" + error); // ログイン画面にリダイレクト
                return;
            }

            // セッションスコープにuserIdを登録
            HttpSession session = request.getSession(); // セッションを取得
            session.setAttribute("userId", user.getUserId()); // セッションにユーザーIDを保存

            logger.info("Login successful for user: " + userName); // ログイン成功をログ出力
            response.sendRedirect("/administer/post/home"); // ホーム画面にリダイレクト
        } catch (Exception e) { // 例外発生時
            logger.severe("Error during login process: " + e.getMessage()); // エラー内容をログ出力
            e.printStackTrace(); // スタックトレースを出力
            String error = java.net.URLEncoder.encode("システムエラーが発生しました。", "UTF-8"); // エラーメッセージをエンコード
            response.sendRedirect("/administer/account/login?error=" + error); // ログイン画面にリダイレクト
        }
    }

    private String hashPassword(String password) throws Failure {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256"); // SHA-256のMessageDigestを取得
            byte[] hash = md.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8)); // パスワードをハッシュ化
            StringBuilder hexString = new StringBuilder(); // 16進数文字列用のStringBuilder
            for (byte b : hash) { // ハッシュ値を1バイトずつ処理
                String hex = Integer.toHexString(0xff & b); // 1バイトを16進数に変換
                if (hex.length() == 1) {
                    hexString.append('0'); // 1桁の場合は0を追加
                }
                hexString.append(hex); // 16進数文字列を追加
            }
            return hexString.toString(); // 完成した16進数文字列を返す
        } catch (java.security.NoSuchAlgorithmException e) { // アルゴリズムが存在しない場合
            throw new Failure("パスワードのハッシュ化に失敗しました。"); // Failure例外をスロー
        }
    }
}
