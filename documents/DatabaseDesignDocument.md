# MiyaBlogデータベース設計書
作成日：2025/4/10
版数：1  
作成者：樋田巧


## 改版履歴
|日付|版数|編集者|変更点|
|-|-|-|-|
|4/10|初版|樋田|- |

### データベース名
miyablog_db

### users
ユーザに対するテーブル
|フィールド名|和名|型|主キー|NULL|その他制約|備考|
|:--|:--|:--|:--|:--|:--|:--|
|user_id|ユーザID|INTEGER|〇|NO|PRIMARY_KEY, AUTO_INCREMENT||
|user_name|ユーザ名|VARCHAR(100)||NO|||
|password|パスワード|VARCHAR(64)||NO||半角英数字の大文字・小文字・数字・記号（ASCIIコード表に載っているもの）から8文字以上32文字以下ですべての種類を組み合わせる。保存時にはSHA-256にてハッシュ化する|

### posts
投稿に対するテーブル
|フィールド名|和名|型|主キー|NULL|その他制約|備考|
|:--|:--|:--|:--|:--|:--|:--|
|post_id|投稿ID|INTEGER|〇|NO|PRIMARY_KEY, AUTO_INCREMENT||
|created_at|作成日時|DATETIME||NO|||
|updated_at|更新日時|DATETIME||YES|||
|post_title|投稿タイトル|VARCHAR(200)||NO|||
|post_text|投稿内容|TEXT||NO|||

## 追加機能実装後
### users
ユーザに対するテーブル
|フィールド名|和名|型|主キー|NULL|その他制約|備考|
|:--|:--|:--|:--|:--|:--|:--|
|user_id|ユーザID|INTEGER|〇|NO|PRIMARY_KEY, AUTO_INCREMENT||
|user_name|ユーザ名|VARCHAR(100)||NO|||
|password|パスワード|VARCHAR(64)||NO||半角英数字の大文字・小文字・数字・記号（ASCIIコード表に載っているもの）から8文字以上32文字以下ですべての種類を組み合わせる。保存時にはSHA-256にてハッシュ化する|

### posts
投稿に対するテーブル
|フィールド名|和名|型|主キー|NULL|その他制約|備考|
|:--|:--|:--|:--|:--|:--|:--|
|post_id|投稿ID|INTEGER|〇|NO|PRIMARY_KEY, AUTO_INCREMENT||
|created_at|作成日時|DATETIME||NO|||
|updated_at|更新日時|DATETIME||YES|||
|post_title|投稿タイトル|VARCHAR(200)||NO|||
|post_text|投稿内容|TEXT||NO|||
|user_id|投稿者のユーザID|INTEGER||NO|FOREIGN_KEY|【変更部分】usersテーブルに対する外部キー|