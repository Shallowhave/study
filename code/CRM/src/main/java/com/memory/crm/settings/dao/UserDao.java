package com.memory.crm.settings.dao;

import com.memory.crm.settings.doman.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    User login(Map<String, String> map);

    List<User> getUserList();
}
