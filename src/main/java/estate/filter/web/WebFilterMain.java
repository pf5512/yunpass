package estate.filter.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kangbiao on 15-10-6.
 * web端过滤器
 */
public class WebFilterMain implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session=request.getSession();
        String path=request.getServletPath();
        ArrayList<String> passUrl=new ArrayList<>();

        passUrl.add("/img/");
        passUrl.add("/js/");
        passUrl.add("/css/");
        passUrl.add("/plugins/");
        passUrl.add("/view/403.html");
        passUrl.add("/view/login.html");
        passUrl.add("/web/auth");

        if (this.isDoFilter(passUrl,path))
        {
            if (session.getAttribute("user")==null)
            {
                response.sendRedirect(request.getContextPath() + "/view/login.html");
                return;
            }
        }
        chain.doFilter(req,res);
    }

    @Override
    public void destroy()
    {

    }

    public boolean isDoFilter(ArrayList<String> paths,String path)
    {
        for (String context:paths)
        {
            if (path.contains(context))
                return false;
        }
        return true;
    }

}
