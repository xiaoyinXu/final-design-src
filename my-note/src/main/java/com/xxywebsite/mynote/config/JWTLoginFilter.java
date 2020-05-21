package com.xxywebsite.mynote.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxywebsite.mynote.bean.RespBean;
import com.xxywebsite.mynote.entity.LoginWarning;
import com.xxywebsite.mynote.entity.User;
import com.xxywebsite.mynote.mapper.MenuMapper;
import com.xxywebsite.mynote.mapper.UserMapper;
import com.xxywebsite.mynote.service.UserService;
import com.xxywebsite.mynote.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

//        req.getInputStream();

        // test
//        EsUtils.findWarningByUserId(1);

        String authorization = ((HttpServletRequest) req).getHeader("Authorization");
        if (authorization == null) {

            MyHttpServletRequestWrapper newRequest = null;
            try {
                // 如果它有Authorization这个字段的话就不读了

                User user = new ObjectMapper()
                        .readValue(req.getInputStream(), User.class);
                // 偷天换日
                Map<String, String> map = new HashMap<>();
                map.put("username", user.getUsername());
                map.put("password", user.getPassword());

                // 我这里再根据username获取userId(如果有的话)
                User user2 = UserUtils.findUserByName(user.getUsername());
                if (user2 != null) {
                    map.put("id", String.valueOf(user2.getId()));
                }
                Long userId = Long.valueOf(user2.getId());
                LoginWarning loginWarning = LoginWarningUtils.findLoginWarningByUserId(userId);
                if (loginWarning != null) {
                    // 发送失败信息
                    HttpServletResponse response = (HttpServletResponse)res;
//                    response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
//                    response.setHeader("Access-Control-Allow-Origin", "*");
                    res.setContentType("application/json;charset=utf-8");
//                    Map<String, Object> map2 = new HashMap<>();
//                    map.put("jwt", TOKEN_PREFIX + " " + JWT);

//                    RespBean respBean = RespBean.ok("登录成功!", map);
                    RespBean respBean = RespBean.error("登录失败,短时间内连续登录失败多次,账号已锁定");
                    ObjectMapper om = new ObjectMapper();
                    PrintWriter out = response.getWriter();
                    out.write(om.writeValueAsString(respBean));
                    out.flush();
                    out.close();


                }

                newRequest = new MyHttpServletRequestWrapper((HttpServletRequest) req, map);
                super.doFilter(newRequest, res, chain);
            } catch (IOException e) {
                // do nothing
                // 我们只需要在登录的时候 发送POST请求的时候...

            }


        } else {
            // 如果authorization != null, 说明已经携带了token
            super.doFilter(req, res, chain);
        }


    }

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;//

    @Autowired
    MenuMapper menuMapper;//

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;


    public static final String topic = "login-info";


    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        User creds = new User();
        creds.setUsername(req.getParameter("username"));
        creds.setPassword(req.getParameter("password"));


        String username = creds.getUsername();
        if (creds != null) {
            // 去ES查看 对应用户是否被锁定
            // 策略修改, 查询mysql
            if (null != req.getParameter("id")) {
//                Long userId = Long.valueOf(req.getParameter("id"));
//                LoginWarning loginWarning = LoginWarningUtils.findLoginWarningByUserId(userId);
//                if (loginWarning != null) {
//                    // 发送失败信息
//
//
//                }
            }


        }

//        SysUser creds = new SysUser(req.getParameter("username"), req.getParameter("password"));
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
//        User user = userService.findUserByName(auth.getName());
        // 往kafka写一条成功登录信息
        Integer id = null;
        if (req.getParameter("id") != null) {
            id = Integer.valueOf(req.getParameter("id"));

        }
        String behavior = "success";
        // 获取时间 2020-03-15 09:17:00
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = now.format(formatter);

        String msg = String.format("%d,%s,%s", id, behavior, time);

//        kafkaTemplate.send(topic, msg);
        KafkaProducerUtils.send(topic, msg);

        TokenAuthenticationService.addAuthentication(res, auth.getName());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        super.unsuccessfulAuthentication(request, response, failed);
        // 往kafka写一条失败登录信息

        // 获取用户id
        Integer id = null;
        if (request.getParameter("id") != null) {
            id = Integer.valueOf(request.getParameter("id"));

        }
        if (id != null && id != 0) {
            // 构造一条失败信息  1,fail,2020-03-15 15:32:00
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String time = LocalDateTime.now().format(formatter);
            String msg = String.format("%d,%s,%s", id, "fail", time);
            KafkaProducerUtils.send(topic, msg);
        }


        response.setContentType("application/json;charset=utf-8");
        RespBean respBean = new RespBean(401, "验证失败", null);
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.write(om.writeValueAsString(respBean));
        out.flush();
        out.close();
    }
}

