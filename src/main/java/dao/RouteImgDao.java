package dao;

import domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * find all the images for the target route object based on rid
     * */
    List<RouteImg> findByRid(int rid);
}
