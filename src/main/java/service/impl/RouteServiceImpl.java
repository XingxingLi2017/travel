package service.impl;

import dao.*;
import dao.impl.*;
import domain.*;
import service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        PageBean<Route> pb = new PageBean<Route>();

        int totalCount = routeDao.findTotolCount(cid, rname);
        // sanity check
        if(pageSize > totalCount) {
            pageSize = totalCount;
        }
        if(pageSize <= 0) {
            pageSize = 5;
        }
        int totalPage = (int) Math.ceil(totalCount * 1.0 / pageSize);
        if(currentPage > totalPage) {
            currentPage = totalPage;
        }
        if(currentPage <= 0) {
            currentPage = 1;
        }

        // load page bean
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        pb.setTotalCount(totalCount);
        List<Route> list = routeDao.findByPage(cid,(currentPage - 1) * pageSize,
                                    pageSize, rname);
        pb.setList(list);
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public Route findOne(int rid) {
        Route route = routeDao.findOne(rid);
        List<RouteImg> imgs = routeImgDao.findByRid(rid);
        route.setRouteImgList(imgs);
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);
        int count = favoriteDao.findCountByRid(rid);
        route.setCount(count);
        Category category = categoryDao.findByCid(route.getCid());
        route.setCategory(category);
        return route;
    }

}
