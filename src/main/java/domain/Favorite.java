package domain;

import java.io.Serializable;


public class Favorite implements Serializable {
    private Route route;    // route object
    private String date;    // date when user liked the route
    private User user;      // user object
    private int uid;        // user id
    private int rid;        // route id

    public Favorite() {
    }

    /**
     * @param route
     * @param date
     * @param user
     */
    public Favorite(Route route, String date, User user) {
            this.route = route;
            this.date = date;
            this.user = user;
            this.uid = user.getUid();
            this.rid = route.getRid();
    }

    public int getUid() {
        return uid;
    }

    public int getRid() {
        return rid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "route=" + route +
                ", date='" + date + '\'' +
                ", user=" + user +
                ", uid=" + uid +
                ", rid=" + rid +
                '}';
    }
}
