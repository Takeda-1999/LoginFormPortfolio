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

### 新規登録のエラー画面
#### 各項目を求める値にするためにValidationで制御する
![Validationエラー画面](images/regist-validation1.png)

#### メールアドレスとパスワードは二重チェック
![isCheckメソッド表示](images/regist-validation2.png)


### ログインのエラー画面
#### メールアドレスとパスワードの照合でユーザーを識別する
![ログインのエラー画面](images/login-error.png)


### ユーザー情報更新のエラー画面
#### 各項目を求める値にするためにValidationで制御する
![更新時のエラー](images/update-validation1.png)

#### メールアドレスの更新時には他のユーザーとメールアドレスが重複していないかチェック
#### またメールアドレスではなくユーザーIDを識別対象として更新する
![メールアドレス更新重複チェック](images/update-validation2.png)


### 削除したユーザーは完全削除ではなく論理削除となっている
### またDB内では一目みてわかるようにフラグ管理をしており、再登録でも同じメールアドレスが登録できるようになっている
![フラグ管理](images/DBever.png)