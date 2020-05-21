package com.xxywebsite.mynote.util;

import com.xxywebsite.mynote.entity.User;
import com.xxywebsite.mynote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    private static UserService userService;

    @Autowired
    void init(UserService userService) {
        UserUtils.userService = userService;
    }

    public static User findUserByName(String username) {

        User user = null;
        try {
            user = userService.findUserByName(username);
        } catch (Exception e) {
            System.out.println("redis没有启动");;
        }
        return user;
    }
}
