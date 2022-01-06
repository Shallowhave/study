package com.memory.crm.workbench.service.impl;

import com.memory.crm.settings.dao.UserDao;
import com.memory.crm.settings.doman.User;
import com.memory.crm.utils.SqlSessionUtil;
import com.memory.crm.vo.ActivityVo;
import com.memory.crm.workbench.dao.ActivityDao;
import com.memory.crm.workbench.dao.ActivityRemarkDao;
import com.memory.crm.workbench.doman.Activity;
import com.memory.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public boolean save(Activity activity) {
        boolean flag = true;
        int count = activityDao.save(activity);
        if (count!=1){
            flag = false;
        }
        return flag;
    }

    public ActivityVo<Activity> pageList(Map<String, Object> map) {
        ActivityVo<Activity> activityVo = new ActivityVo<Activity>();
        List<Activity> activities = activityDao.getActivityListByCondition(map);
        int total = activityDao.getTotalByCondition();
        activityVo.setDataList(activities);
        activityVo.setTotal(total);
        return activityVo;
    }

    public boolean delete(String[] gids) {
        boolean flag = true;
        //查询出需要删除的备注数量
        int count1 = activityRemarkDao.getCountByAids(gids);
        //实际删除的备注数量
        int count2 = activityRemarkDao.deleteByAids(gids);
        if (count1 != count2){
            flag=false;
        }
        int count3 = activityDao.delete(gids);
        if (count3!= gids.length){
            flag=false;
        }

        return flag;
    }

    public Map<String, Object> getActivityList(String id) {
        Map<String, Object> data = new HashMap<String, Object>();
        List<User> userList = userDao.getUserList();
        Activity activity = activityDao.getActivityList(id);

        data.put("userList",userList);
        data.put("activity",activity);
        return data;
    }

    public boolean update(Activity activity) {
        boolean flag = false;
        int count = activityDao.update(activity);
        if (count == 1){
            flag=true;
        }

        return flag;
    }

    public Activity detail(String id) {
        Activity activity = activityDao.detail(id);
        return activity;
    }
}
