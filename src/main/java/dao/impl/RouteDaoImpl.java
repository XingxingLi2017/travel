package dao.impl;

import dao.RouteDao;
import domain.Route;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotolCount(int cid, String rname) {
        StringBuilder sb = new StringBuilder("select COUNT(*) from tab_route where 1=1");
        List<Object> params = new ArrayList<>();
        if(cid > 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && rname.length() > 0) {
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        String sql = sb.toString();
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        StringBuilder sb = new StringBuilder("select * from tab_route where 1=1");
        List<Object> params = new ArrayList<>();
        if(cid > 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && rname.length() > 0) {
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ?,? ");
        params.add(start);
        params.add(pageSize);
        String sql = sb.toString();
        return template.query(sql,
                new BeanPropertyRowMapper<Route>(Route.class),
                params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql,
                new BeanPropertyRowMapper<Route>(Route.class),
                rid);
    }
}
