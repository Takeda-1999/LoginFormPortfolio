## プロジェクト名

このプロジェクトは Spring Boot を使用した「ユーザー管理システム」です。  
ユーザー登録・ログイン・マイページ一覧・更新・ 削除のCRUD操作ができるようになっています。

---

## バージョン
- Java 17
- Spring Boot 3.3.4

---

## 使用技術（依存関係）
- spring-boot-starter-security
- spring-boot-starter-thymeleaf
- spring-boot-starter-validation
- spring-boot-starter-web
- MyBatis 3.0.5
- Oracle JDBC Driver (ojdbc11)
- Lombok
- Spring Boot DevTools

---

## 機能一覧
- ユーザー登録
- ログイン / ログアウト機能
- マイページ表示
- ユーザー情報更新
- ユーザー削除(論理削除)

---

## 画面説明
### GIF
![一連の動作](images/demo.gif)

### 新規登録
#### Validationエラー画面
入力内容に不備があるとエラーメッセージが表示されます。
![Validationエラー画面](images/regist-validation1.png)

### ユーザー情報更新
#### 更新時のエラー
![更新時のエラー](images/sample.png)
