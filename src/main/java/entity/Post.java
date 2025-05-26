package entity;

import java.time.LocalDateTime;
import modelUtil.Failure;

// 投稿情報を表すエンティティクラス
public class Post {
    // 投稿ID（自動採番）
    private int postId;
    // 作成日時
    private LocalDateTime createdAt;
    // 更新日時
    private LocalDateTime updatedAt;
    // 投稿タイトル（200文字以内）
    private String postTitle;
    // 投稿本文
    private String postText;
    // 投稿者ユーザーID
    private int userId;

    /**
     * 投稿エンティティ（Miyablog用）
     * 
     * @param postId    投稿ID（自動採番）
     * @param createdAt 作成日時
     * @param updatedAt 更新日時
     * @param postTitle タイトル（200文字以内）
     * @param postText  本文
     * @param userId    投稿者ユーザーID
     * @throws Failure 制約違反時
     */
    public Post(int postId, LocalDateTime createdAt, LocalDateTime updatedAt,
            String postTitle, String postText, int userId) throws modelUtil.Failure {
        checkPostId(postId);
        checkCreatedAt(createdAt);
        checkPostTitle(postTitle);
        checkPostText(postText);
        checkUserId(userId);
        // 各フィールドに値をセット
        this.postId = postId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.postTitle = postTitle;
        this.postText = postText;
        this.userId = userId;
    }

    // 投稿IDを取得
    public int getPostId() {
        return postId;
    }

    // 作成日時を取得
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // 更新日時を取得
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // 投稿タイトルを取得
    public String getPostTitle() {
        return postTitle;
    }

    // 投稿本文を取得
    public String getPostText() {
        return postText;
    }

    // 投稿者ユーザーIDを取得
    public int getUserId() {
        return userId;
    }

    // 更新日時を設定
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // 投稿タイトルを設定
    public void setPostTitle(String postTitle) throws modelUtil.Failure {
        checkPostTitle(postTitle);
        this.postTitle = postTitle;
    }

    // 投稿本文を設定
    public void setPostText(String postText) throws modelUtil.Failure {
        checkPostText(postText);
        this.postText = postText;
    }

    // 投稿IDのバリデーション
    private void checkPostId(int postId) throws modelUtil.Failure {
        if (postId < 0) {
            throw new modelUtil.Failure("投稿IDは正の整数でなければなりません。");
        }
    }

    // 作成日時のバリデーション
    private void checkCreatedAt(LocalDateTime createdAt) throws modelUtil.Failure {
        if (createdAt == null) {
            throw new modelUtil.Failure("作成日時は必須です。");
        }
    }

    // 投稿タイトルのバリデーション
    private void checkPostTitle(String postTitle) throws modelUtil.Failure {
        if (postTitle == null || postTitle.isEmpty() || postTitle.length() > 200) {
            throw new modelUtil.Failure("投稿タイトルは1文字以上200文字以下である必要があります。");
        }
    }

    // 投稿本文のバリデーション
    private void checkPostText(String postText) throws modelUtil.Failure {
        if (postText == null || postText.isEmpty()) {
            throw new modelUtil.Failure("投稿本文は必須です。");
        }
    }

    // 投稿者ユーザーIDのバリデーション
    private void checkUserId(int userId) throws modelUtil.Failure {
        if (userId <= 0) {
            throw new modelUtil.Failure("投稿者ユーザーIDは正の整数でなければなりません。");
        }
    }
}
