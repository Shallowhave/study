package com.memory.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodongFilter implements Filter {
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html; charset=utf-8");
        //放行过滤器
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
