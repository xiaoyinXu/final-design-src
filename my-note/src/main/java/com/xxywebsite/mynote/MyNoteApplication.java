package com.xxywebsite.mynote;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.sql.SQLOutput;
import java.util.Properties;

@EnableCaching
@MapperScan("com.xxywebsite.mynote.mapper")
@SpringBootApplication
public class MyNoteApplication {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(MyNoteApplication.class, args);
    }

}
