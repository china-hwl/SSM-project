package com.bjpowernode.crm.web.filter;

import com.bjpowernode.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("进入验证登录过滤器");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String path = httpServletRequest.getServletPath();
        if("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){
            chain.doFilter(request,response);
        }else{

            HttpSession session = ((HttpServletRequest) request).getSession();
            User user = (User) session.getAttribute("user");
            if (user!=null){
                chain.doFilter(request,response);
            }else{
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login.jsp");
            }

        }
    }

    @Override
    public void destroy() {

    }
}
