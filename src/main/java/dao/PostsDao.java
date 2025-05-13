package dao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entity.Post;

public class PostsDao {
    private final DataSource dataSource;
    private final ConnectionCloser connectionCloser;

    public PostsDao() {
        this.dataSource = new DataSourceHolder().dataSource;
        this.connectionCloser = new ConnectionCloser();
    }

    public void create(Post post) throws DaoException {
        Connection connection = null;
        String sql = "INSERT INTO posts (created_at, updated_at, post_title, post_text, user_id) VALUES (?, ?, ?, ?, ?)";
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(post.getCreatedAt()));
            statement.setTimestamp(2, post.getUpdatedAt() == null ? null : Timestamp.valueOf(post.getUpdatedAt()));
            statement.setString(3, post.getPostTitle());
            statement.setString(4, post.getPostText());
            statement.setInt(5, post.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("投稿作成時にエラーが発生しました。", e);
        } finally {
            connectionCloser.closeConnection(connection);
        }
    }

    public Post getOne(int postId) throws DaoException {
        Connection connection = null;
        String sql = "SELECT * FROM posts WHERE post_id = ?";
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, postId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Post(
                        rs.getInt("post_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null,
                        rs.getString("post_title"),
                        rs.getString("post_text"),
                        rs.getInt("user_id"));
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException("投稿取得時にエラーが発生しました。", e);
        } finally {
            connectionCloser.closeConnection(connection);
        }
    }

    public List<Post> getAll() throws DaoException {
        Connection connection = null;
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts";
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                posts.add(new Post(
                        rs.getInt("post_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null,
                        rs.getString("post_title"),
                        rs.getString("post_text"),
                        rs.getInt("user_id")));
            }
        } catch (SQLException e) {
            throw new DaoException("投稿一覧取得時にエラーが発生しました。", e);
        } finally {
            connectionCloser.closeConnection(connection);
        }
        return posts;
    }

    public void update(Post post) throws DaoException {
        Connection connection = null;
        String sql = "UPDATE posts SET updated_at = ?, post_title = ?, post_text = ? WHERE post_id = ?";
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, post.getUpdatedAt() == null ? null : Timestamp.valueOf(post.getUpdatedAt()));
            statement.setString(2, post.getPostTitle());
            statement.setString(3, post.getPostText());
            statement.setInt(4, post.getPostId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("投稿更新時にエラーが発生しました。", e);
        } finally {
            connectionCloser.closeConnection(connection);
        }
    }

    public void delete(int postId) throws DaoException {
        Connection connection = null;
        String sql = "DELETE FROM posts WHERE post_id = ?";
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, postId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("投稿削除時にエラーが発生しました。", e);
        } finally {
            connectionCloser.closeConnection(connection);
        }
    }
}
