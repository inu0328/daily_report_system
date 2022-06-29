package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.LikeConverter;
import actions.views.LikeView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Like;

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

    public List<LikeView> getAllPerPage(int page) {

        List<Like> likes = em.createNamedQuery("like.getAll", Like.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return LikeConverter.toViewList(likes);
    }

    /**
     * 指定した日報のいいねデータ件数を取得し、返却する
     * @return いいねデータ件数
     */
    public long countAll(ReportView report) {
        long likes_count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT_ALL_LIKE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .getSingleResult();
        return likes_count;
    }

    private void createInternal(LikeView lv) {

        em.getTransaction().begin();
        em.persist(LikeConverter.toModel(lv));
        em.getTransaction().commit();

    }

    /**
     * 指定した日報データにいいねした人のデータを、指定されたページ数の一覧画面に表示する分取得しLikeViewのリストで返却する
     * @param report 日報
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<LikeView> getLikePerPage(ReportView report, int page) {

        List<Like> Likes = em.createNamedQuery(JpaConst.Q_REP_GET_ALL_LIKE, Like.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return LikeConverter.toViewList(Likes);
    }

    /**
     * 日報idを条件に取得したいいねデータをReportViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public LikeView findOne(int id) {
        //引数は日報ＩＤ。日報ＩＤでいいねテーブルを検索。
        return LikeConverter.toView(findOneInternal(id));
    }

    /**
     * 日報idを条件にいいねデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Like findOneInternal(int id) {
        //引数は日報ＩＤ。日報ＩＤでいいねテーブルを検索し、１件取得するメソッド。
        return em.find(Like.class, id);
    }

    /**
     * 該当日報にログイン中の従業員がいいねした件数を取得し返却
     * @param rv,ev
     * @return 該当日報にログイン中の従業員がいいねした件数
     */
    public long searchLike(ReportView rv,EmployeeView ev) {

        long count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT_LIKED, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(rv))
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(ev))
                .getSingleResult();

        return count;
    }

}

