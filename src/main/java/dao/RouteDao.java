package dao;

import domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * find route total number based on cid and route name
     * */
    int findTotolCount(int cid , String rname);

    /**
     * look up records on current page based on cid, start, pageSize, route name
     * */
    List<Route> findByPage(int cid, int start, int pageSize, String rname);

    /**
     * get route object based on rid
     * */
    Route findOne(int rid);
}
