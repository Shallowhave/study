package com.memory.crm.settings.service.impl;

import com.memory.crm.exception.LoginException;
import com.memory.crm.settings.dao.UserDao;
import com.memory.crm.settings.doman.User;
import com.memory.crm.settings.service.UserService;
import com.memory.crm.utils.DateTimeUtil;
import com.memory.crm.utils.MD5Util;
import com.memory.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userdao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    public User login(String loingAct, String loginPwd, String ip) throws LoginException {
        Map<String, String> count = new HashMap<String, String>();
        count.put("loingAct", loingAct);
        loginPwd= MD5Util.getMD5(loginPwd);
        System.out.println(loginPwd);
        count.put("loginPwd", loginPwd);
        User user = userdao.login(count);
        if (user==null){
            throw new LoginException("账号或者密码错误");

        }
        String expireTime = user.getExpireTime();
        String sysTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(sysTime)<0){
            throw new LoginException("账号已失效");
        }
        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw  new LoginException("账号已锁定");
        }
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new LoginException("ip地址受限");
        }


        return user;
    }



}
