package com.xxywebsite.mynote.mapper;

import com.xxywebsite.mynote.entity.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MenuMapperTest {
    @Autowired
    MenuMapper menuMapper;

    @Test
    public void test1() {
//        Menu[] menus = menuMapper.findMenusByUserIdAndParentId(1, 1);
//        System.out.println(Arrays.toString(menus));
        System.out.println(menuMapper.findMenusByUserIdAndParentId(1, 1));
    }
}