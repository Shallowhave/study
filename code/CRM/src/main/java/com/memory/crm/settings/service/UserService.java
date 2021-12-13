package com.memory.crm.settings.service;

import com.memory.crm.exception.LoginException;
import com.memory.crm.settings.doman.User;

public interface UserService {
    User login(String loingAct, String loginPwd, String ip) throws LoginException;

}
