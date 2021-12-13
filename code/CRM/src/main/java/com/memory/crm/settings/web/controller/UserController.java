package com.memory.crm.settings.web.controller;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;

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

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/settings/user/xxx.do".equals(path)){

        }else if ("/settings/user/xxx.do".equals(path)){

        }
    }
}
