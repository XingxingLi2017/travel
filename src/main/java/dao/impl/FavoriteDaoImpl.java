package dao.impl;

import dao.FavoriteDao;
import domain.Favorite;
import domain.Route;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import util.JDBCUtils;
import util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        String sql = "select * from tab_favorite where rid = ? and uid = ?";
        Favorite f = null;
        try {
            f = template.queryForObject(sql,
                    new BeanPropertyRowMapper<Favorite>(Favorite.class),
                    rid,
                    uid);
        } catch (DataAccessException e) {
//            e.printStackTrace();
        }
        return f;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select Count(*) from tab_favorite where rid = ?";
        return template.queryForObject(sql, Integer.class, rid);
    }

    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?, ?, ?)";
        template.update(sql, rid, new Date(), uid);
    }

    @Override
    public int findCountByUid(int uid) {
        String sql = "select COUNT(*) from tab_favorite where uid = ?";
        return template.queryForObject(sql, Integer.class, uid);
    }

    @Override
    public List<Favorite> findRoutesPageByUid(int uid, int startIdx, int pageSize) {
        String sql = "select * from tab_favorite where uid = ? limit ?,?";
        return template.query(sql,
                new BeanPropertyRowMapper<>(Favorite.class),
                uid,
                startIdx,
                pageSize);
    }

    @Override
    public void remove(int rid, int uid) {
        String sql = "delete from tab_favorite where uid = ? and rid = ?";
        template.update(sql, uid, rid);
    }

    @Override
    public List<Favorite> findFavoriteRank(int startIdx, int pageSize) {
        String sql = " select rid, Count(*) favorits " +
                " from tab_favorite " +
                " group by rid " +
                " ordery by favorits desc " +
                " limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(Favorite.class),
                startIdx,
                pageSize);
    }

    @Override
    public int findCountByFavoriteRank(String rname, double priceFrom, double priceTo) {
        StringBuilder sb = new StringBuilder("select Count(DISTINCT(f.rid)) " +
                " from tab_favorite f left join tab_route r on f.rid = r.rid " +
                " where 1=1 ");
        List<Object> params = new ArrayList<>();
        if(rname != null && !StringUtils.isUndefinedOrNull(rname)) {
            sb.append(" and r.rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" and r.price between ? and ? ");
        params.add(priceFrom);
        params.add(priceTo);

        String sql = sb.toString();
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Favorite> findPageByFavoriteRank(int startIdx, int pageSize, String rname, double priceFrom, double priceTo) {
        StringBuilder sb = new StringBuilder("select f.*, r.*, Count(*) favorites " +
                " from tab_favorite f left join tab_route r on f.rid = r.rid " +
                " where 1=1 ");
        List<Object> params = new ArrayList<>();
        if(rname != null && !StringUtils.isUndefinedOrNull(rname)) {
            sb.append(" and r.rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" and r.price between ? and ? ");
        params.add(priceFrom);
        params.add(priceTo);

        sb.append(" group by f.rid " +
                " order by favorites desc " +
                " limit ?,? ");
        params.add(startIdx);
        params.add(pageSize);
        String sql = sb.toString();
        return template.query(sql, new RowMapper<Favorite>() {
            @Override
            public Favorite mapRow(ResultSet resultSet, int i) throws SQLException {
                Route route = new Route();
                Favorite fav = new Favorite();
                route.setRid(resultSet.getInt("rid"));
                route.setCid(resultSet.getInt("cid"));
                route.setPrice(resultSet.getDouble("price"));
                route.setRname(resultSet.getString("rname"));
                route.setRouteIntroduce(resultSet.getString("routeIntroduce"));
                route.setSid(resultSet.getInt("sid"));
                route.setCount(resultSet.getInt("favorites"));
                route.setRimage(resultSet.getString("rimage"));
                route.setRflag(resultSet.getString("rflag"));
                route.setRdate(resultSet.getString("rdate"));
                route.setIsThemeTour(resultSet.getString("isThemeTour"));
                route.setSourceId(resultSet.getString("sourceId"));
                fav.setDate(resultSet.getString("date"));
                fav.setRid(resultSet.getInt("rid"));
                fav.setRoute(route);
                return fav;
            }
        }, params.toArray());
    }
}
