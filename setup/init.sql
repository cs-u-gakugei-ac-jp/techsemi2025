-- 【データベースの作成方法】

-- 【データベースの作成方法】
-- docker exec -i techsemi2025-db-1 mysql -uroot -proot < /Users/t.toida/techsemi2025/setup/init.sql

-- データベースの作成
CREATE DATABASE miyablog_db;
USE miyablog_db;

-- users テーブルの作成
CREATE TABLE users (
    user_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL
);

-- デフォルトユーザーの挿入
INSERT INTO users (user_id, user_name, password) VALUES (1, 'admin', 'password123');

-- posts テーブルの作成
CREATE TABLE posts (
    post_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    post_title VARCHAR(200) NOT NULL,
    post_text TEXT NOT NULL,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);