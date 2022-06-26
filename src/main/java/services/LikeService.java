package services;

import java.time.LocalDateTime;

import actions.views.LikeView;

/**
 * いいね一覧の操作に関わる処理を行うクラス
 */
public class LikeService extends ServiceBase {

    /**
     * 画面から入力された従業員の登録内容を元にデータを1件作成し、従業員テーブルに登録する
     * @param lv 画面から入力された従業員の登録内容
     */
    public void create(LikeView lv) {

        //登録日時、更新日時は現在時刻を設定する
        LocalDateTime now = LocalDateTime.now();
        lv.setCreatedAt(now);
        lv.setUpdatedAt(now);

        //データを登録する
        create(lv);

    }

}

