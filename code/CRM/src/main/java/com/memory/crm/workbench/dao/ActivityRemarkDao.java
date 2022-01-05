package com.memory.crm.workbench.dao;

import com.memory.crm.workbench.doman.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityRemarkDao {
    int getCountByAids(String[] gids);

    int deleteByAids(String[] gids);

    List<ActivityRemark> showActiviRemarkByAid(String activityId);

    void deleteRemarkById(String id);

    int saveRemark(Map<String, Object> map);

    int updateRemark(Map<String, Object> map);
}
