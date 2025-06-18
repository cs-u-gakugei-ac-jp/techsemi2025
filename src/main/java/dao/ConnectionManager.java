// データベース接続を管理するユーティリティクラス
// JDBCドライバのロードと、DB接続情報の保持、コネクション取得メソッドを提供
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    // MariaDB用ドライバ名
    private static final String RDB_DRIVE = "com.mysql.cj.jdbc.Driver";
    // DB接続URL
    private static final String URL = "jdbc:mysql://db:3306/miyablog_db?characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Tokyo&rewriteBatchedStatements=true";
    // DBユーザー名
    private static final String USER = "root";
    // DBパスワード
    private static final String PASSWD = "root";

    static {
        try {
            // MySQL用ドライバを明示的にロード
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBCドライバのロードに失敗しました", e);
        }
    }

    // DBコネクションを取得するメソッド
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWD);
    }
}