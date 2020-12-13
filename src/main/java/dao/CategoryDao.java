package dao;

import domain.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * Find all the categories in nav bar
     * */
    List<Category> findAll();

    /**
     * find category by cid
     * @param cid
     * @return
     */
    Category findByCid(int cid);
}
