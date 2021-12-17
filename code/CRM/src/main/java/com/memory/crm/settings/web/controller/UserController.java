package com.memory.crm.settings.web.controller;

import com.memory.crm.settings.doman.User;
import com.memory.crm.settings.service.UserService;
import com.memory.crm.settings.service.impl.UserServiceImpl;
import com.memory.crm.utils.MD5Util;
import com.memory.crm.utils.PrintJson;
import com.memory.crm.utils.ServiceFactory;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import com.sun.org.apache.xpath.internal.functions.FuncFalse;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Service;
import java.io.IOException;
import java.security.Provider;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        System.out.println(path);
        if ("/settings/user/login.do".equals(path)) {
            loing(request, response);

        } else if ("/settings/user/xxx.do".equals(path)) {

        }
    }

    private void loing(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行controller登录");
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        System.out.println(loginAct);
        loginPwd = MD5Util.getMD5(loginPwd);
        System.out.println(loginPwd);
        String ip = request.getRemoteAddr();
        // System.out.println(ip);
        // String ip2= request.getLocalAddr();
        // System.out.println(ip2);
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        System.out.println("获得了userService对象");

        try {
            User user = userService.login(loginAct, loginPwd, ip);
            request.getSession().setAttribute("user", user);
            //{"success":true}
            // String str ="{\"success\":true}";
            // response.getWriter().print(str);
            PrintJson.printJsonFlag(response, true);

        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", false);
            map.put("msg", msg);
            // response.setContentType("text/html; charset=UTF-8");
            // response.setCharacterEncoding("utf-8");
            PrintJson.printJsonObj(response, map);


        }


    }


}
