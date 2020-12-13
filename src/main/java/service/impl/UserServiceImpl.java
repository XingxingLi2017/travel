package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.User;
import service.UserService;
import util.MailUtils;
import util.UuidUtil;


public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    /**
     * register new user and send activation email
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user, String contextURL) {
        User u = userDao.findByUsername(user.getUsername());
        if(u != null) {
            return false;
        }
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userDao.save(user);
        // send activation email
        String content =
                "<a href = '"+contextURL+"/user/active?code="+user.getCode()+"'>Click to activate your TravelSite account</a>";
        MailUtils.sendMail(user.getEmail(), content, "Activate Travel account");
        return true;
    }

    /**
     * active specified user
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        User user = userDao.findByCode(code);
        if(user != null) {
            userDao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * get login user based on input username and password
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
