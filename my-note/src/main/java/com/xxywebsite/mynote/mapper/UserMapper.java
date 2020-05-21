package com.xxywebsite.mynote.mapper;

import com.xxywebsite.mynote.entity.Menu;
import com.xxywebsite.mynote.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface UserMapper {
    //获取UserById
    @Select("select * from user where id = #{id}")
    User findUserById(int id);

    User findUserByName(String name);

    User findUserWithMenuById(int id);
}
