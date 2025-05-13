package entity;

import modelUtil.Failure;

public class User {
    private int userId;
    private String userName;
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
        checkUserId(userId);
        checkUserName(userName);
        this.password = password;
        this.userId = userId;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) throws Failure {
        checkUserName(userName);
        this.userName = userName;
    }

    public void setPassword(String password) throws Failure {
        checkPassword(password);
        this.password = password;
    }

    private void checkUserId(int userId) throws Failure {
        if (userId < 0) {
            throw new Failure("ユーザーIDは正の整数でなければなりません。");
        }
    }

    private void checkUserName(String userName) throws Failure {
        if (userName == null || userName.length() > 100) {
            throw new Failure("ユーザー名は1文字以上100文字以下である必要があります。");
        }
    }

    private void checkPassword(String password) throws Failure {
        if (password == null || password.isEmpty()) {
            throw new Failure("パスワードは空であってはなりません。");
        }
    }
}
