package com.xxywebsite.mynote.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
//@WebFilter(urlPatterns = "/*",filterName = "channelFilter")
public class MyFilter2 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("2 init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println(3);
        filterChain.doFilter(servletRequest, servletResponse);
//        System.out.println(4);
    }

    @Override
    public void destroy() {
//        System.out.println("2 destory");
    }
}


