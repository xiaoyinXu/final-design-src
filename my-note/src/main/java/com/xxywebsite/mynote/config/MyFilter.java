package com.xxywebsite.mynote.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;

@Component
//@WebFilter(urlPatterns = "/*",filterName = "channelFilter")
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("1 init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println(1);
//        PrintWriter writer = servletResponse.getWriter();
//        writer.write("456");
        filterChain.doFilter(servletRequest, servletResponse);
//        System.out.println(2);
    }

    @Override
    public void destroy() {
        System.out.println("1 destroy");
    }
}

