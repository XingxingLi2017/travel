package dao;

import domain.User;

public interface UserDao {
    /**
     * find user by user name
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * save user into tab_user
     * @param user
     */
    void save(User user);

    /**
     * update user's status into Y , which represents the user is activated
     * @param user
     */
    void updateStatus(User user);

    /**
     * find user by activation code
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * find user by username and password
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username, String password);

    /**
     * find user by uid
     * @param uid
     * @return
     */
    User findByUid(int uid);
}
