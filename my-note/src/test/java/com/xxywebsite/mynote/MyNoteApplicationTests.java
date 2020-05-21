package com.xxywebsite.mynote;

import com.xxywebsite.mynote.entity.User;
import com.xxywebsite.mynote.es.EsNote;
import com.xxywebsite.mynote.service.UserService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@SpringBootTest
class MyNoteApplicationTests {

    @Autowired
    UserService userService;

//    @Autowired
//    ElasticsearchTemplate elasticsearchTemplate;

//    @Autowired
//    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    void contextLoads() {

        stringRedisTemplate.opsForValue().set("person1", "asd");
        redisTemplate.opsForValue().set("person2",new User(1,"cookie","sad",null));//        System.setProperty("es.set.netty.runtime.available.processors", "false");
//        EsNote esNote;
//        esNote = new EsNote(1, 1, "设计模式", "这就是设计模式的内容",LocalDate.now());
//        IndexQuery build = new IndexQueryBuilder()
//                .withId(esNote.getId().toString())
//                .withObject(esNote)
//                .build();
//        String documentId = elasticsearchTemplate.index(build);
//        System.out.println(documentId);

//        userService.findUserByName("cookie");
//        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

}
