package com.memory.crm.workbench.service.impl;

import com.memory.crm.utils.SqlSessionUtil;
import com.memory.crm.workbench.dao.ActivityRemarkDao;
import com.memory.crm.workbench.doman.ActivityRemark;
import com.memory.crm.workbench.service.ActivityRemarkService;

import java.util.List;
import java.util.Map;

public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);

    public List<ActivityRemark> showActiviRemarkByAid(String activityId) {
        List<ActivityRemark> activityRemarkList = activityRemarkDao.showActiviRemarkByAid(activityId);
        return activityRemarkList;
    }

    public void deleteRemarkById(String id) {
        activityRemarkDao.deleteRemarkById(id);
    }

    public boolean saveRemrk(Map<String, Object> map) {
        int count = activityRemarkDao.saveRemark(map);
        boolean flag=false;
        if (count == 1){
            flag = true;
        }

        return flag;
    }

    public boolean updateRemark(Map<String, Object> map) {
        int count= activityRemarkDao.updateRemark(map);
        boolean flag = true;
        if (count != 1){
            flag = false;
        }

        return flag;
    }
}
