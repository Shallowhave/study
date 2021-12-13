package com.memory.crm.settings.service.impl;

import com.memory.crm.settings.dao.UserDao;
import com.memory.crm.settings.service.UserService;
import com.memory.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class UserServiceImpl implements UserService {
    private UserDao userdao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);



}
