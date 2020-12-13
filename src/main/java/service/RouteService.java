package service;

import domain.PageBean;
import domain.Route;

/**
 * Service for travel route
 * */
public interface RouteService {
    /**
     * page query based on category
     * */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);
    /**
     * query route object based on rid
     * */
    Route findOne(int rid);
}
