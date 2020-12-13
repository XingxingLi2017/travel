package web.servlet;

import domain.ResultInfo;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import service.impl.UserServiceImpl;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService  service = new UserServiceImpl();
    /**
     * Register User function
     * */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // check verification code
        String check = request.getParameter("check");
        String CHECKCODE_SERVER = (String)request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");

        if(CHECKCODE_SERVER == null || !CHECKCODE_SERVER.equalsIgnoreCase(check)) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("verification code is wrong");
            writeValue(info, response);
            return;
        }

        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // register user in database, if username exists, fail
        String requestURL = request.getRequestURL().toString();     // http://domain/travel/user/regist

        requestURL = StringUtils.truncatURLBySlash(requestURL, 2);
        boolean flag = service.regist(user, requestURL);
        ResultInfo info = new ResultInfo();

        // response result information
        if(flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("register fail, Username already exists!");
        }
        writeValue(info, response);
    }
    /**
     * Login Function
     * */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        // check verification code
        String checkCode = map.get("check")[0];
        String CHECKCODE_SERVER = (String)request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        if(CHECKCODE_SERVER == null || !CHECKCODE_SERVER.equalsIgnoreCase(checkCode)) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("Verification code is wrong");
            writeValue(info, response);
            return;
        }

        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        User u = service.login(user);

        // give response information
        ResultInfo info = new ResultInfo();
        if(u == null) {
            // wrong username and password
            info.setFlag(false);
            info.setErrorMsg("username or password is wrong");
        } else {
            // user's not been activated
            if(!"Y".equalsIgnoreCase(u.getStatus())) {
                info.setFlag(false);
                info.setErrorMsg("you haven't activated your account.");
            } else {
                request.getSession().setAttribute("user", u);
                info.setFlag(true);
            }
        }
        writeValue(info, response);
    }
    /**
     * find current login user
     * */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        User res = null;
        if(user != null) {
            res = new User();
            res.setUid(user.getUid());
            res.setUsername(user.getUsername());
            res.setName(user.getName());
        }
        writeValue(res, response);
    }
    /**
     * activate target user according to activation code
     * */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code != null) {
            boolean flag = service.active(code);
            String msg = null;
            if(flag) {
                msg = "Activate successfully, please <a href='"+request.getContextPath()+"/login.html'>login</a>";
            } else {
                msg = "Activate failed, please contact us.";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
    /**
     * Logout function
     * */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/index.html");
    }
}
