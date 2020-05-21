package com.xxywebsite.mynote.config;

import com.xxywebsite.mynote.entity.User;
import com.xxywebsite.mynote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.PostConstruct;

@Configuration
public class UserIdMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired(required = false)
    UserService userService;

    private static UserIdMethodArgumentResolver util;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
       return parameter.hasParameterAnnotation(UserId.class);
    }

    @PostConstruct
    public void init() {
        util = this;
//        util.userService = this.userService;
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();
        String username = auth.getName();
        User user = util.userService.findUserByName(username);
        int userId = user.getId();

        return userId;
    }
}
