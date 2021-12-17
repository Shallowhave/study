package com.memory.crm.settings.service;

import com.memory.crm.exception.LoginException;
import com.memory.crm.settings.doman.User;

import java.util.List;

public interface UserService {
    User login(String loingAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();


}
