# アーキテクチャー原則

## 技術要素

SpringBoot3.2
Java17
Maven3
Windows上で実行します
初期段階では、HSQLを使う

JavaとMavenはインストール済みなので、セットアップは不要です。
また、Mavenプロジェクトも初期セットアップ済みです。

## ターゲットコードに関する原則

### アーキテクチャー（レイヤー構造）

以下のレイヤー構造に準じてください

- RestController(DTOに依存する。Entityは直接使わない）
- Service(Entiyに依存してよい。したがって、DTOとEntityの変換はこのレイヤーでやる）
- Repository(SpringDataJPAを用いる）
- Entity

その他共通

- 例外処理はRestCotrollerでやらずに、Advisorに委譲する
- AOPを用いて、RestController、Serviceのpublicメソッドの開始、終了時にInfoログを出力する

### パッケージ構造

大きくは以下の構造にしてください。

RepositoyとEntityは全ユースケースで共通
RestController、Sevidceはfeature(usecase)単位のパッケージの下に置く（今回はproject-management.以下に配置するという意味）

## テストコードに関する原則

### テストの種類

- ダイヤモンド型アプローチに沿って、テストはService層の単位で作るのを基本とする
  - その際に、Service層が依存するレイヤーについては、原則として、Mockではなく本物を使う

- 特に複雑なドメインロジックやRepositoryについては、個別のテストを追加すること

### テストで使うライブラリ

- JUnit5
- @SpringBootTest
- org.assertj

### 書き方

- 各メソッドで、@Sqlを用いて、テストデータをセットアップすること
- メソッドに複数のテスト観点を混在させたないこと
  - 正常系、エラー系、境界値、異常系など、それぞれのテスト観点について、メソッドを分割すること
- テストメソッド毎に、ドイツ語でJavadocを書くこと
