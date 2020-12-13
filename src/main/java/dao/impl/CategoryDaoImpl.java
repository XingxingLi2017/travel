package dao.impl;

import dao.CategoryDao;
import domain.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        return template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
    }

    @Override
    public Category findByCid(int cid) {
        String sql = "select * from tab_category where cid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Category>(Category.class), cid);
    }
}
