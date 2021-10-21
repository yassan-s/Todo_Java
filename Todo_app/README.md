# Todo app

## Spring Boot CRUD処理の基礎を押さえる

* スターター,プロジェクト依存関係
	* Spring Boot DevTools(コードを変更した時に、自動的に再起動してくれる)
	* JDBC API
	* H2 DateBase
	* Thymeleaf
	* Spring Web

## メモ

* request ~ response までの流れ
1. フォームの送信やURLのアクセスにより、クライアントからrequestが来る
2. URLに応じて「Dispathcher Servletクラス」が「Controllerクラス」に処理を渡す
  -> 自動でやってくれる
3. URLのアクセス以降の処理をControllerクラスが行う
  -> Modelクラス(リクエストスコープ)にhtmlデータを作成するときに必要なデータを保存する
4. Viewが表示される

## eclipse

* Git
	* push するときのユーザー名
		githubに連携するときのユーザーは、「git」
		githubのユーザー名と間違えないこと

	* commit push するとき
		プロジェクトを右クリック -> チーム

	* 「.Gitignore」ファイルの表示
		1. プロジェクト・エクスプローラーのタブ右下、矢印のアイコンをクリック
  		(フィルター表示をさせる)
		2. *リソースのチェックを外す


* setter,getter
	* 対象のjavaファイルを選択 -> メニューバーの「ソース」-> getter,setterの生成
