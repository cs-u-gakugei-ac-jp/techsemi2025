package modelUtil;

// アプリケーション固有の例外を表すクラス
public class Failure extends Exception {
    // メッセージと原因例外を指定してFailure例外を生成
    public Failure(String message, Throwable cause) {
        super(message, cause);
    }

    // メッセージのみ指定してFailure例外を生成
    public Failure(String message) {
        super(message);
    }
}