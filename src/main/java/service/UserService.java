package service;

import domain.User;

public interface UserService {
    /**
     * get user info and save into database
     * if username is already used , return false, otherwise return true
     * @param user
     * @return
     */
    boolean regist(User user, String contextPath);

    /**
     * change user's activation status based on activation code
     * return false if activation code is wrong
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * get user login info and check if user is registered and activated
     * return null if login info is wrong
     * @param user
     * @return
     */
    User login(User user);
}
