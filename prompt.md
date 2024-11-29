SpringBootのRestControllerで、プロジェクト情報をCRUDするエンドポイントを作ってください。

## 機能

プロジェクト情報は、一旦以下の項目を保持するものとします。

- ID（主キーとなるもの）
- プロジェクト名
- プロジェクト主管部署のID
- プロジェクトランク（SS,A,B,C,Dといったランクが定義されているものとする）
- PM氏名
- PL氏名
- 開始年月日
- 終了年月日
- 売上金額
- 備考

必要な機能
CRUD機能一式
但し、Readについては、複数の条件で検索が可能でかつページングを行ってください。
主キーを指定した単一Readも実装してください。

## 技術要素

SpringBoot3.2
Java17
Maven3
Windows上で実行します
初期段階では、HSQLを使う

JavaとMavenはインストール済みなので、セットアップは不要です。
また、Mavenプロジェクトも初期セットアップ済みです。

## アーキテクチャー（レイヤー構造）

以下のレイヤー構造に準じてください

- RestController(DTOに依存する。Entityは直接使わない）
- Service(Entiyに依存してよい。したがって、DTOとEntityの変換はこのレイヤーでやる）
- Repository(SpringDataJPAを用いる）
- Entity

その他共通

- 例外処理はRestCotrollerでやらずに、Advisorに委譲する
- AOPを用いて、RestController、Serviceのpublicメソッドの開始、終了時にInfoログを出力する

## パッケージ構造

大きくは以下の構造にしてください。

RepositoyとEntityは全ユースケースで共通
RestController、Sevidceはfeature(usecase)単位のパッケージの下に置く（今回はproject-management.以下に配置するという意味）
