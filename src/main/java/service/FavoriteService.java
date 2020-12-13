package service;

import domain.Favorite;
import domain.PageBean;
import domain.Route;

public interface FavoriteService {

    /**
     * find if user specified by uid likes the route specified by rid
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(String rid, int uid);

    /**
     * add the target rout to the target user's favorite
     * @param rid
     * @param uid
     */
    void add(String rid, int uid);

    /**
     * find the user's favorite routes based on current page
     * @param uid
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean<Favorite> findFavoriteByPage(String uid, String currentPage, int pageSize);

    /**
     * remove route from user's favorate
     * @param ridStr
     * @param uid
     */
    void remove(String ridStr, int uid);

    /**
     * favorate rank page, which have conditions rname, price to, price from
     * @param currentPage
     * @param pageSize
     * @param rname
     * @param priceFrom
     * @param priceTo
     * @return
     */
    PageBean<Favorite> findFavoriteRankPage(String currentPage, int pageSize, String rname, String priceFrom, String priceTo);
}
