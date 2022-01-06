package com.memory.crm.workbench.service;

import com.memory.crm.vo.ActivityVo;
import com.memory.crm.workbench.doman.Activity;

import java.util.Map;

public interface ActivityService {
    boolean save(Activity activity);

    ActivityVo<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] gids);

    Map<String, Object> getActivityList(String id);

    boolean update(Activity activity);

    Activity detail(String id);
}
