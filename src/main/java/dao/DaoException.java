// DAO層で発生した例外を表す独自例外クラス
package dao;

public class DaoException extends Exception {
    // 例外メッセージと原因例外を受け取るコンストラクタ
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}