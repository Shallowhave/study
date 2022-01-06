package com.memory.crm.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String servletPath = request.getServletPath();
        if ("/login.jsp".equals(servletPath) || "/settings/user/login.do".equals(servletPath)){

            filterChain.doFilter(request, response);

        }else {

            Object user = session.getAttribute("user");
            if (user != null){
                filterChain.doFilter(request, response);
            }else {
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }

        }

    }
}
