package com.xxywebsite.mynote.config;

import com.xxywebsite.mynote.service.LoginUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    LoginUserDetailsService loginUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());



//        auth.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("cookie")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("admin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/test/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                UsernamePasswordAuthenticationFilter.class)
                // And filter other requests to check the presence of JWT in header
                .addFilterBefore(new JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .formLogin().usernameParameter("username").passwordParameter("password")
                            .loginProcessingUrl("/login").loginPage("/login");
    }
}
