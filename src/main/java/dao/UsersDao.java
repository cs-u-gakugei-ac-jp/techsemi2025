// ユーザー（User）テーブルに対するDB操作を行うDAOクラス
package dao;

import java.sql.*;
import entity.User;
import modelUtil.Failure;

public class UsersDao {

    // ユーザーを新規作成するメソッド
    public void create(User user) throws DaoException {
        System.out.println("Inserting user: " + user.getUserName() + ", " + user.getPassword());
        String sql = "INSERT INTO users (user_name, password) VALUES (?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword()); // ハッシュ化されたパスワードを保存
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            throw new DaoException("ユーザー作成時にエラーが発生しました。", e);
        }
    }

    // ユーザーIDで1件取得するメソッド
    public User getOne(int userId) throws DaoException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    try {
                        return new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("password"));
                    } catch (Failure e) {
                        System.err.println("Error creating User object: " + e.getMessage());
                        throw new DaoException("ユーザーオブジェクトの作成中にエラーが発生しました。", e);
                    }
                }
            }
            return null;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            throw new DaoException("ユーザー取得時にエラーが発生しました。", e);
        }
    }

    // ユーザー名で1件取得するメソッド
    public User getOneByUserName(String userName) throws DaoException {
        String sql = "SELECT * FROM users WHERE user_name = ?";
        try (Connection connection = ConnectionManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    try {
                        return new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("password"));
                    } catch (Failure e) {
                        System.err.println("Error creating User object: " + e.getMessage());
                        throw new DaoException("ユーザーオブジェクトの作成中にエラーが発生しました。", e);
                    }
                }
            }
            return null;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            throw new DaoException("ユーザー取得時にエラーが発生しました。", e);
        }
    }

    // ユーザー情報を更新するメソッド
    public void update(User user) throws DaoException {
        String sql = "UPDATE users SET user_name = ?, password = ? WHERE user_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("ユーザー更新時にエラーが発生しました。", e);
        }
    }

    // ユーザーを削除するメソッド
    public void delete(int userId) throws DaoException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection connection = ConnectionManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("ユーザー削除時にエラーが発生しました。", e);
        }
    }
}
