package dao;

import javax.sql.DataSource;
import java.sql.*;
import entity.User;
import modelUtil.Failure;

public class UsersDao {
    private final DataSource dataSource;
    private final ConnectionCloser connectionCloser;

    public UsersDao() {
        this.dataSource = new DataSourceHolder().dataSource;
        this.connectionCloser = new ConnectionCloser();
    }

    public void create(User user) throws DaoException {
        System.out.println("Inserting user: " + user.getUserName() + ", " + user.getPassword());

        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "INSERT INTO users (user_name, password) VALUES (?, ?)";
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword()); // ハッシュ化されたパスワードを保存
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            throw new DaoException("ユーザー作成時にエラーが発生しました。", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close statement: " + e.getMessage());
                }
            }
            connectionCloser.closeConnection(connection);
        }
    }

    public User getOne(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            rs = statement.executeQuery();
            if (rs.next()) {
                try {
                    return new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("password"));
                } catch (Failure e) {
                    System.err.println("Error creating User object: " + e.getMessage());
                    throw new DaoException("ユーザーオブジェクトの作成中にエラーが発生しました。", e);
                }
            }
            return null;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            throw new DaoException("ユーザー取得時にエラーが発生しました。", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close ResultSet: " + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close statement: " + e.getMessage());
                }
            }
            connectionCloser.closeConnection(connection);
        }
    }

    public User getOneByUserName(String userName) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE user_name = ?";
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            rs = statement.executeQuery();
            if (rs.next()) {
                try {
                    return new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("password"));
                } catch (Failure e) {
                    System.err.println("Error creating User object: " + e.getMessage());
                    throw new DaoException("ユーザーオブジェクトの作成中にエラーが発生しました。", e);
                }
            }
            return null;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            throw new DaoException("ユーザー取得時にエラーが発生しました。", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close ResultSet: " + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close statement: " + e.getMessage());
                }
            }
            connectionCloser.closeConnection(connection);
        }
    }

    public void update(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "UPDATE users SET user_name = ?, password = ? WHERE user_id = ?";
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("ユーザー更新時にエラーが発生しました。", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close statement: " + e.getMessage());
                }
            }
            connectionCloser.closeConnection(connection);
        }
    }

    public void delete(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "DELETE FROM users WHERE user_id = ?";
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("ユーザー削除時にエラーが発生しました。", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close statement: " + e.getMessage());
                }
            }
            connectionCloser.closeConnection(connection);
        }
    }
}
