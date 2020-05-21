package com.xxywebsite.mynote.es;

import com.xxywebsite.mynote.bean.WarningInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EsLoginWarningRepositoryTest {
    @Autowired
    EsLoginWarningRepository repository;

    @Test
    public void test1() {
        List<WarningInfo> byCount = repository.findByCount(3L);
        System.out.println(byCount);
    }
}