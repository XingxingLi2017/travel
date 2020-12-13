package web.servlet;

import domain.Favorite;
import domain.PageBean;
import domain.Route;
import domain.User;
import service.FavoriteService;
import service.RouteService;
import service.impl.FavoriteServiceImpl;
import service.impl.RouteServiceImpl;
import sun.reflect.annotation.ExceptionProxy;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    /**
     * Get routes in current page based on category id, current page number, route name
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String rnameStr = request.getParameter("rname");
        String cidStr = request.getParameter("cid");
        if(rnameStr != null) {
            rnameStr = new String(rnameStr.getBytes("iso-8859-1"), "utf-8");
            if("null".equalsIgnoreCase(rnameStr)) {
                rnameStr = null;
            }
        }
        // transform string into int
        int cid = 0;
        // defaut current page number is 1
        int currentPage = 1;
        // default page size is 5
        int pageSize = 5;

        try{
            if(cidStr != null && cidStr.length() > 0) {
                cid = Integer.parseInt(cidStr);
            }
        }
        catch (NumberFormatException e) {}
        try{
            if(currentPageStr != null && currentPageStr.length() > 0) {
                currentPage = Integer.parseInt(currentPageStr);
            }
        }
        catch (NumberFormatException e) {}
        try{
            if(pageSizeStr != null && pageSizeStr.length() > 0) {
                pageSize = Integer.parseInt(pageSizeStr);
            }
        }
        catch (NumberFormatException e) {}

        PageBean<Route> pageBean = routeService.pageQuery(cid, currentPage, pageSize , rnameStr);
        writeValue(pageBean, response);
    }

    /**
     * find the target route detailed info based on rid
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String ridStr = request.getParameter("rid");
        int rid = 1;
        try{
            rid = Integer.parseInt(ridStr);
        } catch (NumberFormatException e) {}
        Route route = routeService.findOne(rid);
        writeValue(route, response);
    }

    /**
     * check if current user liked current route
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        int uid = 0;
        if(user == null) {
            uid = 0;        // there is no uid which is 0
        } else {
            uid = user.getUid();
        }
        boolean flag = favoriteService.isFavorite(rid, uid);
        writeValue(flag, response);
    }

    /**
     * Add route to user's favorite
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        int uid = 0;
        if(user == null) {
            return ;
        } else {
            uid = user.getUid();
        }
        favoriteService.add(rid, uid);
    }

    /**
     * Remove route in user's favorite
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void removeFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        int uid = 0;
        if(user == null) {
            return ;
        } else {
            uid = user.getUid();
        }
        favoriteService.remove(rid, uid);
    }

    /**
     * Find all the routes in the user's favorite
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findFavoriteByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String currentPage = request.getParameter("currentPage");
        int pageSize = 12;
        if(user == null) {
            return;
        }
        PageBean<Favorite> pageBean = favoriteService.findFavoriteByPage(user.getUid()+"", currentPage, pageSize);
        writeValue(pageBean, response);
    }

    /**
     * Get Favorite Rank page. Find routes rank based on route name, price from , price to contions
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findFavoriteRankPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPage = request.getParameter("currentPage");
        String rname = request.getParameter("rname");
        String priceFrom = request.getParameter("priceFrom");
        String priceTo = request.getParameter("priceTo");
        int pageSize = 8;
        PageBean<Favorite> pb = favoriteService.findFavoriteRankPage(currentPage, pageSize, rname, priceFrom, priceTo);
        writeValue(pb, response);
    }

}
