package FilterPackage;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "FilterPackage.FilterAuthChecker")
public class FilterAuthChecker implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String obtUri = ((HttpServletRequest)req).getRequestURI().substring(((HttpServletRequest)req).getContextPath
                ().length()+1);
        HttpSession session = ((HttpServletRequest)req).getSession();
        String setPath;

        //if(session!=null){
            if(obtUri.contains("user/")){
                setPath = obtUri.substring(5);
                if(!setPath.isEmpty()){
                    req.setAttribute("title",setPath);
                }
                ((HttpServletResponse)resp).setHeader("Cache-Control", "no-cache, no-store, must-revalidate");// HTTP 1.1.
                ((HttpServletResponse)resp).setHeader("Pragma", "no-cache"); // HTTP 1.0.
                ((HttpServletResponse)resp).setHeader("Expires", "0"); //Proxy
                req.getRequestDispatcher("/LoginLanding.jsp").forward(req,resp);
            }else
                if(obtUri.contains("/Logout")){
                    req.getRequestDispatcher("/Logout").forward(req,resp);
            }else
                if(obtUri.contentEquals("/Upload")){
                    req.getRequestDispatcher("/Upload").forward(req,resp);
            }else
                if(obtUri.contentEquals("/DataServe")){
                    req.getRequestDispatcher("/DataServe").forward(req,resp);
            }
       /* }else{
            req.getRequestDispatcher("/index.jsp").include(req,resp);
        }*/

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
