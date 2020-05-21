package com.xxywebsite.mynote.mapper;

import com.xxywebsite.mynote.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void test1() {
//        User userById = userMapper.findUserById(1);
//        User cookie = userMapper.findUserByName("cookie");
//        System.out.println(cookie);
        User user = userMapper.findUserWithMenuById(1);
        System.out.println(user);
    }

}