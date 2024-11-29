# プロジェクト管理システム API

プロジェクト情報を管理するためのRESTful APIです。プロジェクトのCRUD操作、検索機能、およびページング機能を提供します。

## 技術スタック

- Java 17
- Spring Boot 3.1.0
- Spring Data JPA
- HSQLDB (インメモリデータベース)
- Maven 3.x
- Lombok

## アーキテクチャ

レイヤードアーキテクチャを採用し、以下の層に分かれています：

- Controller層：クライアントとのインターフェース
- Service層：ビジネスロジックとDTO-Entity変換
- Repository層：データアクセス
- Entity層：データモデル

### パッケージ構造

```
com.example.demo
├── common
│   ├── advisor
│   ├── aspect
│   ├── dto
│   └── exception
└── feature
    └── project_management
        ├── controller
        ├── dto
        └── service
```

## 機能

### プロジェクト情報の管理

以下の項目を管理します：

- ID（主キー）
- プロジェクト名
- プロジェクト主管部署のID
- プロジェクトランク（SS,A,B,C,D）
- PM氏名
- PL氏名
- 開始年月日
- 終了年月日
- 売上金額
- 備考

### APIエンドポイント

#### CRUD操作

- プロジェクト作成
  ```
  POST /api/projects
  ```

- プロジェクト取得
  ```
  GET /api/projects/{id}
  ```

- プロジェクト更新
  ```
  PUT /api/projects/{id}
  ```

- プロジェクト削除
  ```
  DELETE /api/projects/{id}
  ```

#### 検索機能

```
GET /api/projects?projectName=xxx&departmentId=1&projectRank=A&page=0&size=20
```

検索条件：
- プロジェクト名（部分一致）
- 部署ID
- プロジェクトランク
- PM氏名（部分一致）
- PL氏名（部分一致）
- 開始日範囲
- 終了日範囲
- 売上金額範囲

### エラーハンドリング

- 404 Not Found：リソースが存在しない場合
- 400 Bad Request：バリデーションエラーの場合
- 500 Internal Server Error：予期せぬエラーの場合

エラーレスポンス例：
```json
{
  "timestamp": "2024-11-29T11:53:31.870+09:00",
  "status": 404,
  "error": "Not Found",
  "message": "Project not found with id: 1",
  "path": "/api/projects/1"
}
```

### クロスカッティング機能

- AOPによるログ出力
  - Controller層とService層のメソッド開始・終了時にログを出力
  - SQLクエリのログ出力

- バリデーション
  - 必須項目チェック
  - プロジェクトランクの値チェック

## 開発環境のセットアップ

1. 前提条件
   - Java 17
   - Maven 3.x

2. ビルドと実行
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. アクセス
   ```
   http://localhost:8080/api/projects
   ```

## 設定

application.propertiesで以下の設定が可能：

- データベース設定
- ページングのデフォルト値
- ログレベル

## ライセンス

This project is licensed under the MIT License.
