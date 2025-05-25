package entity;

import modelUtil.Failure;

// ユーザー情報を表すエンティティクラス
public class User {
    // ユーザーID（自動採番）
    private int userId;
    // ユーザー名（100文字以内）
    private String userName;
    // パスワード（SHA-256ハッシュ済み、8〜32文字）
    private String password;

    /**
     * ユーザーエンティティ（Miyablog用）
     * 
     * @param userId   ユーザーID（自動採番）
     * @param userName ユーザー名（100文字以内）
     * @param password パスワード（SHA-256ハッシュ済み、8〜32文字）
     * @throws Failure 制約違反時
     */
    public User(int userId, String userName, String password) throws Failure {
        checkUserId(userId); // ユーザーIDのバリデーション
        checkUserName(userName); // ユーザー名のバリデーション
        this.password = password; // パスワードをセット
        this.userId = userId; // ユーザーIDをセット
        this.userName = userName; // ユーザー名をセット
    }

    // ユーザーIDを取得
    public int getUserId() {
        return userId;
    }

    // ユーザー名を取得
    public String getUserName() {
        return userName;
    }

    // パスワードを取得
    public String getPassword() {
        return password;
    }

    // ユーザー名を設定（バリデーションあり）
    public void setUserName(String userName) throws Failure {
        checkUserName(userName);
        this.userName = userName;
    }

    // パスワードを設定（バリデーションあり）
    public void setPassword(String password) throws Failure {
        checkPassword(password);
        this.password = password;
    }

    // ユーザーIDのバリデーション
    private void checkUserId(int userId) throws Failure {
        if (userId < 0) {
            throw new Failure("ユーザーIDは正の整数でなければなりません。");
        }
    }

    // ユーザー名のバリデーション
    private void checkUserName(String userName) throws Failure {
        if (userName == null || userName.length() > 100) {
            throw new Failure("ユーザー名は1文字以上100文字以下である必要があります。");
        }
    }

    // パスワードのバリデーション
    private void checkPassword(String password) throws Failure {
        if (password == null || password.isEmpty()) {
            throw new Failure("パスワードは空であってはなりません。");
        }
    }
}
