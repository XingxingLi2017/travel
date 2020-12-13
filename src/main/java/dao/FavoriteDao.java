package dao;

import domain.Favorite;

import java.util.List;

public interface FavoriteDao {

    /**
     * find Favorite Object based on rid and uid
     * @param rid
     * @param uid
     * @return
     */
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * find how many people like the route
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * add the route to the user's favorite
     * @param rid
     * @param uid
     */
    void add(int rid, int uid);

    /**
     * get the total routes in the user's favorite
     * @param uid
     * @return
     */
    int findCountByUid(int uid);

    /**
     * get the user's favorites based on page
     * @param uid
     * @param startIdx
     * @param pageSize
     * @return
     */
    List<Favorite> findRoutesPageByUid(int uid, int startIdx, int pageSize);

    /**
     * remove route from user's favorate
     * @param rid
     * @param uid
     */
    void remove(int rid, int uid);

    /**
     * get favorites in current page
     * @param startIdx
     * @param pageSize
     * @return
     */
    List<Favorite> findFavoriteRank(int startIdx, int pageSize);

    /**
     * find count by rname, price from , price to
     * @param rname
     * @param priceFrom
     * @param priceTo
     * @return
     */
    int findCountByFavoriteRank(String rname, double priceFrom, double priceTo);

    /**
     * find favorite route rank page by rname, price from, price to
     * @param startIdx
     * @param pageSize
     * @param rname
     * @param priceFrom
     * @param priceTo
     * @return
     */
    List<Favorite> findPageByFavoriteRank(int startIdx, int pageSize, String rname, double priceFrom, double priceTo);
}
