package com.xxywebsite.mynote.service;

import com.xxywebsite.mynote.entity.User;
import com.xxywebsite.mynote.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "user")
public class UserService {
    @Autowired
    UserMapper userMapper;

    @Cacheable(cacheNames = "user", key = "#name")
    public User findUserByName(String name) {
        return userMapper.findUserByName(name);
    }
}
