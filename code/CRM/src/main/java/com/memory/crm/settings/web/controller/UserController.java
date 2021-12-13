package com.memory.crm.settings.web.controller;

import com.memory.crm.settings.service.UserService;
import com.memory.crm.settings.service.impl.UserServiceImpl;
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
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/settings/user/login.do".equals(path)){
            loing(req,resp);

        }else if ("/settings/user/xxx.do".equals(path)){

        }
    }

    private void loing(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("执行controller登录");
        String loginAct = req.getParameter("loginAct");
        String loginPwd = req.getParameter("loginPwd");
        String ip = req.getRemoteAddr();
        UserService user = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try {
            user.login(loginAct, loginPwd, ip);
            req.getSession().setAttribute("user",user);
            //{"success":true}
            // String str ="{\"sucess\":true}";
            // resp.getWriter().print(str);
            PrintJson.printJsonFlag(resp,true);
        }catch (Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(resp,map);


        }




    }


}
