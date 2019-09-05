package com.xuhui.tbk_manager_server.service;

import com.xuhui.tbk_manager_server.bean.User;

public interface RedisService {
    String insertUserToRedis(User getUser);

    Boolean checkToken(String token);
}
