package controller.administer.post; // パッケージ宣言

import java.io.IOException; // IOExceptionのインポート
import javax.servlet.ServletException; // ServletExceptionのインポート
import javax.servlet.annotation.WebServlet; // WebServletアノテーションのインポート
import javax.servlet.http.HttpServlet; // HttpServletクラスのインポート
import javax.servlet.http.HttpServletRequest; // HttpServletRequestクラスのインポート
import javax.servlet.http.HttpServletResponse; // HttpServletResponseクラスのインポート

import dao.ConnectionTester; // ConnectionTesterクラスのインポート

@WebServlet("/administer/post/connectoin-test") // サーブレットのURLマッピング
public class ConnectionTestServlet extends HttpServlet { // HttpServletを継承したクラス定義
    protected void doGet(HttpServletRequest request, HttpServletResponse response) // GETリクエストを処理するメソッド
            throws ServletException, IOException { // 例外のスロー宣言

        ConnectionTester connectionTester = new ConnectionTester(); // ConnectionTesterのインスタンス生成
        connectionTester.test(); // DB接続テストの実行
    }
}