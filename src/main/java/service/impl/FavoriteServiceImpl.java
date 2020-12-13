package service.impl;

import dao.FavoriteDao;
import dao.RouteDao;
import dao.UserDao;
import dao.impl.FavoriteDaoImpl;
import dao.impl.RouteDaoImpl;
import dao.impl.UserDaoImpl;
import domain.Favorite;
import domain.PageBean;
import domain.Route;
import domain.User;
import service.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        return favorite != null;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid), uid);
    }

    @Override
    public PageBean<Favorite> findFavoriteByPage(String uidStr, String currentPageStr, int pageSize) {
        // parse string
        int uid = 0;
        try{
            uid = Integer.parseInt(uidStr);
        } catch (NumberFormatException e) { }

        int currentPage = 1;
        try{
            currentPage = Integer.parseInt(currentPageStr);
        } catch (NumberFormatException e) { }
        int totalCount = favoriteDao.findCountByUid(uid);
        if(pageSize > totalCount) {
            pageSize = totalCount;
        }
        if(pageSize < 0) {
            pageSize = 12;
        }

        int totalPage = (int)Math.ceil(totalCount * 1.0 / pageSize);

        // sanity check
        if(currentPage > totalPage) {
            currentPage = totalPage;
        }
        if(currentPage <= 0) {
            currentPage = 1;
        }


        int startIdx = (currentPage - 1) * pageSize;
        List<Favorite> list = favoriteDao.findRoutesPageByUid(uid, startIdx, pageSize);
        for(Favorite fav : list) {
            Route route = routeDao.findOne(fav.getRid());
            User temp = userDao.findByUid(fav.getUid());
            User user = new User();
            user.setUid(temp.getUid());
            user.setName(temp.getName());
            fav.setUser(user);
            fav.setRoute(route);
        }
        PageBean<Favorite> pb = new PageBean<>();
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);
        pb.setPageSize(pageSize);
        pb.setCurrentPage(currentPage);
        pb.setList(list);
        return pb;
    }

    @Override
    public void remove(String ridStr, int uid) {
        int rid = Integer.parseInt(ridStr);
        favoriteDao.remove(rid, uid);
    }

    @Override
    public PageBean<Favorite> findFavoriteRankPage(String currentPageStr, int pageSize, String rname, String priceFromStr, String priceToStr) {
        int currentPage = 1;
        try {
            currentPage = Integer.parseInt(currentPageStr);
        } catch (Exception e) {}
        double priceFrom = 0;
        try {
            priceFrom = Double.parseDouble(priceFromStr);
        } catch (Exception e) {}
        double priceTo = Double.MAX_VALUE;
        try {
            priceTo = Double.parseDouble(priceToStr);
        } catch (Exception e) {}

        int totalCount = favoriteDao.findCountByFavoriteRank(rname, priceFrom, priceTo);
        if(pageSize > totalCount) pageSize = totalCount;
        if(pageSize <= 0) pageSize = 8;

        int totalPage = (int)Math.ceil(totalCount * 1.0 / pageSize);
        if(currentPage > totalPage) currentPage = totalPage;
        if(currentPage <= 0) currentPage = 1;

        int startIdx = (currentPage - 1) * pageSize;
        List<Favorite> list = favoriteDao.findPageByFavoriteRank(startIdx, pageSize, rname, priceFrom, priceTo);

        PageBean<Favorite> pb = new PageBean<>();
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        pb.setList(list);
        return pb;
    }
}
