# 性能

あなたはJavaに習熟したエンジニアです。
コードを"rule"で示したルールにて評価し、改善案をMarkdownの表形式で示してください。ただし、以下は厳守してください。

- 表以外は表示しないこと
- 改善点については、具体的な修正箇所がどこかを示すこと

## 表

表のカラムは次のとおりとしてください。

- 観点
- 結果（⚪︎、×、△、-）
  - ⚪︎: 問題なし
  - ×: 問題あり
  - △: 軽微な問題あり
  - -: 対象外
- 改善点

## rule

以下すべてのルールに対してレビューしてください。

- 大きな配列やコレクションを扱う際、メモリ効率の良い方法を使用していること
- 大量データ処理時に、メモリ効率の良いストリーム処理を使用していること
- 可能な場合、オブジェクト型の代わりにプリミティブ型を使用していること
- ボクシング/アンボクシングの回数を最小限に抑えていること
- バッファリングを適切に使用していること
- 正規表現を繰り返し使う場合は事前にコンパイルしていること
- リフレクションを使用していないこと
