package com.xxywebsite.mynote.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxywebsite.mynote.bean.RespBean;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyList;

public class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 1000*60*60*24*1; // 1 days
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    static void addAuthentication(HttpServletResponse res, String username) throws IOException {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
        res.setHeader("Access-Control-Allow-Origin","*");
//        res.setStatus(200);
//        res.getWriter().write(TOKEN_PREFIX + " " + JWT);
//        ObjectMapper om = new ObjectMapper();
//        RespBean.ok()

        res.setContentType("application/json;charset=utf-8");
        Map<String, Object> map = new HashMap<>();
        map.put("jwt", TOKEN_PREFIX + " " + JWT);
        RespBean respBean = RespBean.ok("登录成功!", map);

        ObjectMapper om = new ObjectMapper();
        PrintWriter out = res.getWriter();
        out.write(om.writeValueAsString(respBean));
        out.flush();
        out.close();
        ;
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
        }
        return null;
    }
}
