package com.memory.crm.workbench.web.controller;

import com.memory.crm.settings.doman.User;
import com.memory.crm.settings.service.UserService;
import com.memory.crm.settings.service.impl.UserServiceImpl;
import com.memory.crm.utils.DateTimeUtil;
import com.memory.crm.utils.PrintJson;
import com.memory.crm.utils.ServiceFactory;
import com.memory.crm.utils.UUIDUtil;
import com.memory.crm.vo.ActivityVo;
import com.memory.crm.workbench.doman.Activity;
import com.memory.crm.workbench.service.ActivityService;
import com.memory.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/workbench/activity/getUserList.do".equals(path)) {
            getUserList(request, response);

        } else if ("/workbench/activity/save.do".equals(path)) {
            save(request, response);
        } else if("/workbench/activity/pageList.do".equals(path)){
            pageList(request,response);
        } else if("/workbench/activity/delete.do".equals(path)) {
            delete(request, response);

        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        String[] gids =request.getParameterValues("id");
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.delete(gids);
        PrintJson.printJsonFlag(response,flag);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        String name= request.getParameter("search-name");
        String owner= request.getParameter("search-owner");
        String startTime= request.getParameter("search-startTime");
        String endTime= request.getParameter("search-endTime");
        String pageNoStr= request.getParameter("pageNo");
        String pageSizeStr= request.getParameter("pageSize");
        int pageNo = Integer.valueOf(pageNoStr);
        int pageSize = Integer.valueOf(pageSizeStr);
        int skipCount = (pageNo-1)*pageSize;
        map.put("search-name",name);
        map.put("search-owner",owner);
        map.put("search-startTime",startTime);
        map.put("search-endTime",endTime);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        ActivityVo<Activity> activityVo = activityService.pageList(map);
        PrintJson.printJsonObj(response,activityVo);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        //生成uuid
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        Activity activity = new Activity(id, owner, name, startDate, endDate, cost, description, createTime, createBy);
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.save(activity);
        PrintJson.printJsonFlag(response, flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = userService.getUserList();
        PrintJson.printJsonObj(response, userList);
    }
}
