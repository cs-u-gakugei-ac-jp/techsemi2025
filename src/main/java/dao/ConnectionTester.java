// DB接続のテストを行うためのクラス
package dao;

import java.sql.*;

public class ConnectionTester {

    // 接続用の情報をフィールドに定数として定義
    public static String RDB_DRIVE = "com.mysql.cj.jdbc.Driver";
    public static String URL = "jdbc:mysql://db:3306/miyablog_db?characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Tokyo&rewriteBatchedStatements=true";
    public static String USER = "root";
    public static String PASSWD = "root";

    // DB接続テストを行うメソッド
    public void test() {
        try {
            // ドライバのロード
            Class.forName(RDB_DRIVE);
            // DBへ接続
            Connection con = DriverManager.getConnection(URL, USER, PASSWD);

            // 接続成功メッセージとコネクション情報の表示
            System.out.println("JDBCデータベース接続成功");
            System.out.println("con = " + con);

            con.close();
        } catch (Exception e) {
            // 接続失敗時のエラーメッセージ
            System.out.println("JDBCデータベース接続エラー:" + e);
        } finally {
            // テスト終了メッセージ
            System.out.println("JDBCデータベース接続終了");
        }
    }
}