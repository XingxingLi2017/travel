package dao;

import domain.Seller;

public interface SellerDao {
    /**
     * find target seller by sid
     * @param sid
     * @return
     */
    Seller findById(int sid);
}
