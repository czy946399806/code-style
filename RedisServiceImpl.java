package com.xuhui.tbk_manager_server.service.impl;

import com.xuhui.tbk_manager_server.bean.User;
import com.xuhui.tbk_manager_server.common.TokenProccessor;
import com.xuhui.tbk_manager_server.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public String insertUserToRedis(User getUser) {
        String token = TokenProccessor.getInstance().makeToken();
        redisTemplate.opsForValue().set(String.valueOf(getUser.getId()),token);
        redisTemplate.opsForValue().set(token,String.valueOf(getUser.getId()));
        return token;
    }

    @Override
    public Boolean checkToken(String token) {
        Object userId = redisTemplate.opsForValue().get(token);
        Object currentToken = redisTemplate.opsForValue().get(userId);
        return currentToken.toString().equals(token);
    }
}
