package dao.impl;


import dao.UserDao;
import domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * find specific user by username
     * */
    @Override
    public User findByUsername(String username) {
        String sql = "select * from tab_user where username = ?";
        User user = null;
        try{
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class) , username);
            return user;
        }
        catch (Exception e) {

        }
        return user;
    }
    /**
     * save user information into tab_user
     * */
    @Override
    public void save(User user) {
        String sql = "insert into tab_user(" +
                " username, password, name, " +
                " birthday, sex, telephone, email, " +
                " status, code ) " +
                " values(? , ? , ?, ? , ? , ? ,? , ? , ? ) ";
        template.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
                );
    }
    /**
     * update user's activated status
     * */
    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        template.update(sql , user.getUid());
    }
    /**
     * find user by activation code
     * */
    @Override
    public User findByCode(String code) {
        String sql = "select * from tab_user where code = ?";
        User user = null;
        try {
            user = template.queryForObject(sql,
                    new BeanPropertyRowMapper<User>(User.class),
                    code);
        } catch (DataAccessException e) {
//            e.printStackTrace();
        }
        return user;
    }

    /**
     * query user based on username and password
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "select * from tab_user where username = ? and password = ?";
        User user = null;
        try{
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class) , username, password);
            return user;
        }
        catch (Exception e) {

        }
        return user;
    }

    @Override
    public User findByUid(int uid) {
        String sql = "select * from tab_user where uid = ?";
        User user = null;
        try {
            user = template.queryForObject(sql,
                    new BeanPropertyRowMapper<User>(User.class),
                    uid);
        } catch (DataAccessException e) {
//            e.printStackTrace();
        }
        return user;
    }
}
