package com.xxywebsite.mynote.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class RedisConfig {
    @Bean
//    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setKeySerializer(new GenericJackson2JsonRedisSerializer());
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
//        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setKeySerializer(new GenericJackson2JsonRedisSerializer());
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
//        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
