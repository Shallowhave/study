package com.memory.crm.workbench.service;

import com.memory.crm.workbench.doman.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityRemarkService {
    List<ActivityRemark> showActiviRemarkByAid(String activityId);

    void deleteRemarkById(String id);

    boolean saveRemrk(Map<String, Object> map);

    boolean updateRemark(Map<String, Object> map);
}
