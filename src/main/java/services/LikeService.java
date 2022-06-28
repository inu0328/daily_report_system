package services;

import java.time.LocalDateTime;

import actions.views.LikeConverter;
import actions.views.LikeView;

/**
 * いいね一覧の操作に関わる処理を行うクラス
 */
public class LikeService extends ServiceBase {

    public void create(LikeView lv) {

            LocalDateTime ldt = LocalDateTime.now();
            lv.setCreatedAt(ldt);
            lv.setUpdatedAt(ldt);
            createInternal(lv);

    }

    /**
     * 指定されたページ数の一覧画面に表示するいいね一覧データを取得し、LikeViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
/*
    public List<LikeView> getAllPerPage(int page) {

        List<Like> likes = em.createNamedQuery("Like.getAll", Like.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return LikeConverter.toViewList(likes);
    }
*/
    /**
     * 日報テーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
/*    public long countAll() {
        long likes_count = (long) em.createNamedQuery("like.count", Long.class)
                .getSingleResult();
        return likes_count;
    }
*/
    private void createInternal(LikeView lv) {

        em.getTransaction().begin();
        em.persist(LikeConverter.toModel(lv));
        em.getTransaction().commit();

    }

}

