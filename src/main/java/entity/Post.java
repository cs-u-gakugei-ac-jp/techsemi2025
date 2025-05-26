package entity;

import java.time.LocalDateTime;

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
     */
    public Post(int postId, LocalDateTime createdAt, LocalDateTime updatedAt,
            String postTitle, String postText, int userId) {
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
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    // 投稿本文を設定
    public void setPostText(String postText) {
        this.postText = postText;
    }
}
