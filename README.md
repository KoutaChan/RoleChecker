# RoleChecker (現在のバージョンは1.3です)

**設定ファイルを見る:** [config.yml](https://github.com/KoutaChan/RoleChecker/blob/main/src/main/resources/config.yml)

**releases:** [releases](https://github.com/KoutaChan/RoleChecker/releases/)

## これは何？

このマインクラフトのプラグインは、Discordと併用して使います

Discordユーザーの役職を取得してマインクラフトのプレイヤーが参加可能か確認します

## 使い方

1. [config.yml](https://github.com/KoutaChan/RoleChecker/blob/main/src/main/resources/config.yml) のBotトークンなどを設定します

わからない場合は [Discord botユーザを作成し、トークンを取得する手順](https://cod-sushi.com/discord-py-token/) を参照してください

2. [最新バージョンをダウンロード](https://github.com/KoutaChan/RoleChecker/releases/download/HotFix-1.3/RoleChecker-1.3.jar) からダウンロードします

3. マインクラフトのプラグインフォルダに`RoleChecker-1.3.jar`を入れるだけ

## Discordコマンド

`!help` ヘルプを表示します

`!check {MCID}` ユーザーが参加可能か確認します

`!join {MCID}` ユーザーをデータベースに登録させます

`!remove {MCID}` ユーザーをデータベースから削除させます

`!forcejoin {MCID} {DiscordID}` [Adminパーミッションが必要] ユーザーを強制的にデータベースに登録させます

`!forceremove {MCID} {DiscordID}` [Adminパーミッションが必要] ユーザーを強制的にデータベースから削除させます

## Minecraftコマンド

`/joinmode {boolean}` 参加可能か変更

`/removedatabase` データベースを再生成します

## バンジーコードの場合

UUIDの発行がバグっている場合があります、ご了承ください(ご自身で設定して修正してください)

## Licence

[MIT License](https://github.com/KoutaChan/RoleChecker/blob/main/LICENSE)


## API
[API Example](https://github.com/KoutaChan/RoleChecker/blob/main/src/main/java/me/koutachan/rolechecker/api/event/testAPI.java)

## Util
[SQL Util](https://github.com/KoutaChan/RoleChecker/blob/main/src/main/java/me/koutachan/rolechecker/util/SQLUtil.java)

[UUID Util](https://github.com/KoutaChan/RoleChecker/blob/main/src/main/java/me/koutachan/rolechecker/util/UUIDUtil.java)

[Check Util](https://github.com/KoutaChan/RoleChecker/blob/main/src/main/java/me/koutachan/rolechecker/util/Check.java)
