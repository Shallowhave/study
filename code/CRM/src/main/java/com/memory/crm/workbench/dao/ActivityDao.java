package com.memory.crm.workbench.dao;


import com.memory.crm.workbench.doman.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {

    int save(Activity activity);


    int getTotalByCondition();

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int delete(String[] gids);

    Activity getActivityList(String id);

    int update(Activity activity);

    Activity detail(String id);
}
