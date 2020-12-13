package dao.impl;

import dao.SellerDao;
import domain.RouteImg;
import domain.Seller;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findById(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        return template.queryForObject(sql,
                new BeanPropertyRowMapper<>(Seller.class),
                sid);
    }
}
