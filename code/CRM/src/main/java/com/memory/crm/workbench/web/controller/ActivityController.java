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
import com.memory.crm.workbench.doman.ActivityRemark;
import com.memory.crm.workbench.service.ActivityRemarkService;
import com.memory.crm.workbench.service.ActivityService;
import com.memory.crm.workbench.service.impl.ActivityRemarkServiceImpl;
import com.memory.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        }else if("/workbench/activity/edit.do".equals(path)) {
            edit(request, response);

        }else if("/workbench/activity/updateActivity.do".equals(path)) {
            update(request, response);

        }else if("/workbench/activity/detail.do".equals(path)) {
            detail(request, response);

        }else if("/workbench/activity/showActivityRemarkByAid.do".equals(path)) {
            showActiviRemarkByAid(request, response);

        }else if("/workbench/activity/deleteRemarkById.do".equals(path)) {
            deleteRemarkById(request, response);

        }else if("/workbench/activity/saveRemark.do".equals(path)) {
            saveRemark(request, response);

        }else if("/workbench/activity/updateRemark.do".equals(path)) {
            updateRemark(request, response);

        }
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        String editTime = DateTimeUtil.getSysTime();
        User user = (User) request.getSession().getAttribute("user");
        String editBy = user.getName();
        String editFlag = "1";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("noteContent",noteContent);
        map.put("editTime",editTime);
        map.put("editBy",editBy);
        map.put("editFlag",editFlag);
        ActivityRemarkService activityRemarkService= (ActivityRemarkService) ServiceFactory.getService(new ActivityRemarkServiceImpl());
        boolean flag = activityRemarkService.updateRemark(map);
        PrintJson.printJsonFlag(response,flag);


    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        String activityId = request.getParameter("activityId");
        String noteContent = request.getParameter("noteContent");
        String createBy = request.getParameter("careteBy");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("activityId",activityId);
        map.put("noteContent",noteContent);
        map.put("createBy",createBy);
        map.put("id",id);
        map.put("createTime",createTime);
        ActivityRemarkService activityRemarkService = (ActivityRemarkService) ServiceFactory.getService(new ActivityRemarkServiceImpl());
        boolean flag = activityRemarkService.saveRemrk(map);
        PrintJson.printJsonFlag(response,flag);
    }

    private void deleteRemarkById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityRemarkService activityRemarkService= (ActivityRemarkService) ServiceFactory.getService(new ActivityRemarkServiceImpl());
        activityRemarkService.deleteRemarkById(id);
    }

    private void showActiviRemarkByAid(HttpServletRequest request, HttpServletResponse response) {
        String activityId = request.getParameter("activityId");
        ActivityRemarkService activityRemarkService= (ActivityRemarkService) ServiceFactory.getService(new ActivityRemarkServiceImpl());
        List<ActivityRemark> activityRemarkList= activityRemarkService.showActiviRemarkByAid(activityId);
        PrintJson.printJsonObj(response,activityRemarkList);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity activity = activityService.detail(id);
        request.setAttribute("a",activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        String id= request.getParameter("id");
        System.out.println("#############################################");
        System.out.println(id);
        String owner= request.getParameter("owner");
        String name= request.getParameter("name");
        String startDate= request.getParameter("startDate");
        String endDate= request.getParameter("endDate");
        String cost= request.getParameter("cost");
        String description= request.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)(request.getSession().getAttribute("user"))).getName();
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.update(activity);
        PrintJson.printJsonFlag(response,flag);


    }

    private void edit(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String, Object> activity= activityService.getActivityList(id);
        PrintJson.printJsonObj(response,activity);

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        String[] ids =request.getParameterValues("id");
        ActivityService activityService= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.delete(ids);
        PrintJson.printJsonFlag(response,flag);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("pageList执行了。。。。。。。。。。。。");
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
