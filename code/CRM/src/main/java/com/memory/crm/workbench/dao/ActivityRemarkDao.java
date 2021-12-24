package com.memory.crm.workbench.dao;

public interface ActivityRemarkDao {
    int getCountByAids(String[] gids);

    int deleteByAids(String[] gids);
}
