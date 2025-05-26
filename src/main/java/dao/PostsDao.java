// 投稿（Post）テーブルに対するDB操作を行うDAOクラス
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entity.Post;

public class PostsDao {

    // 投稿を新規作成するメソッド
    public void create(Post post) throws DaoException {
        String sql = "INSERT INTO posts (created_at, updated_at, post_title, post_text, user_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection(); // DB接続を取得
                PreparedStatement statement = connection.prepareStatement(sql)) { // SQL文を準備
            statement.setTimestamp(1, Timestamp.valueOf(post.getCreatedAt())); // 作成日時をセット
            statement.setTimestamp(2, post.getUpdatedAt() == null ? null : Timestamp.valueOf(post.getUpdatedAt())); // 更新日時をセット（null許容）
            statement.setString(3, post.getPostTitle()); // 投稿タイトルをセット
            statement.setString(4, post.getPostText()); // 投稿本文をセット
            statement.setInt(5, post.getUserId()); // 投稿者ユーザーIDをセット
            statement.executeUpdate(); // SQL実行（INSERT）
        } catch (SQLException e) {
            throw new DaoException("投稿作成時にエラーが発生しました。", e); // 例外発生時はDaoExceptionでラップ
        }
    }

    // 投稿IDで1件取得するメソッド
    public Post getOne(int postId) throws DaoException {
        String sql = "SELECT * FROM posts WHERE post_id = ?"; // 投稿IDで検索するSQL
        try (Connection connection = ConnectionManager.getConnection(); // DB接続を取得
                PreparedStatement statement = connection.prepareStatement(sql)) { // SQL文を準備
            statement.setInt(1, postId); // 投稿IDをセット
            try (ResultSet rs = statement.executeQuery()) { // SQL実行（SELECT）
                if (rs.next()) { // 結果があれば
                    try {
                        return new Post(
                                rs.getInt("post_id"), // 投稿ID
                                rs.getTimestamp("created_at").toLocalDateTime(), // 作成日時
                                rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime()
                                        : null, // 更新日時（null許容）
                                rs.getString("post_title"), // タイトル
                                rs.getString("post_text"), // 本文
                                rs.getInt("user_id")); // ユーザーID
                    } catch (modelUtil.Failure f) {
                        throw new DaoException("投稿取得時にエンティティ生成エラーが発生しました。", f);
                    }
                }
            }
            return null; // 見つからなければnull
        } catch (SQLException e) {
            throw new DaoException("投稿取得時にエラーが発生しました。", e); // 例外発生時
        }
    }

    // 全投稿をリストで取得するメソッド
    public List<Post> getAll() throws DaoException {
        List<Post> posts = new ArrayList<>(); // 結果格納用リスト
        String sql = "SELECT * FROM posts"; // 全件取得SQL
        try (Connection connection = ConnectionManager.getConnection(); // DB接続
                PreparedStatement statement = connection.prepareStatement(sql); // SQL準備
                ResultSet rs = statement.executeQuery()) { // SQL実行
            while (rs.next()) { // 1件ずつ取り出し
                try {
                    posts.add(new Post(
                            rs.getInt("post_id"), // 投稿ID
                            rs.getTimestamp("created_at").toLocalDateTime(), // 作成日時
                            rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime()
                                    : null, // 更新日時
                            rs.getString("post_title"), // タイトル
                            rs.getString("post_text"), // 本文
                            rs.getInt("user_id"))); // ユーザーID
                } catch (modelUtil.Failure f) {
                    throw new DaoException("投稿一覧取得時にエンティティ生成エラーが発生しました。", f);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("投稿一覧取得時にエラーが発生しました。", e); // 例外発生時
        }
        return posts; // 結果リストを返す
    }

    // 投稿を更新するメソッド
    public void update(Post post) throws DaoException {
        String sql = "UPDATE posts SET updated_at = ?, post_title = ?, post_text = ? WHERE post_id = ?"; // 更新SQL
        try (Connection connection = ConnectionManager.getConnection(); // DB接続
                PreparedStatement statement = connection.prepareStatement(sql)) { // SQL準備
            statement.setTimestamp(1, post.getUpdatedAt() == null ? null : Timestamp.valueOf(post.getUpdatedAt())); // 更新日時
            statement.setString(2, post.getPostTitle()); // タイトル
            statement.setString(3, post.getPostText()); // 本文
            statement.setInt(4, post.getPostId()); // 投稿ID
            statement.executeUpdate(); // SQL実行（UPDATE）
        } catch (SQLException e) {
            throw new DaoException("投稿更新時にエラーが発生しました。", e); // 例外発生時
        }
    }

    // 投稿を削除するメソッド
    public void delete(int postId) throws DaoException {
        String sql = "DELETE FROM posts WHERE post_id = ?"; // 削除SQL
        try (Connection connection = ConnectionManager.getConnection(); // DB接続
                PreparedStatement statement = connection.prepareStatement(sql)) { // SQL準備
            statement.setInt(1, postId); // 投稿ID
            statement.executeUpdate(); // SQL実行（DELETE）
        } catch (SQLException e) {
            throw new DaoException("投稿削除時にエラーが発生しました。", e); // 例外発生時
        }
    }
}
