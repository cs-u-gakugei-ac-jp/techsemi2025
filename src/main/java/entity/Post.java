package entity;

import java.time.LocalDateTime;

public class Post {
    private int postId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String postTitle;
    private String postText;
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
        this.postId = postId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.postTitle = postTitle;
        this.postText = postText;
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostText() {
        return postText;
    }

    public int getUserId() {
        return userId;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }
}
