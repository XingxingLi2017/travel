package web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/*")
public class CharacterFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String method = request.getMethod();
        // convert charset to UTF-8
        if(method.equalsIgnoreCase("post")){
            request.setCharacterEncoding("utf-8");
        }
        // set response charset to utf-8
        response.setContentType("text/html;charset=utf-8");
        chain.doFilter(request,response);
    }

    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
