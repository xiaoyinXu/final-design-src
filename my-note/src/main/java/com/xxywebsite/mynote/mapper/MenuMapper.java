package com.xxywebsite.mynote.mapper;

import com.xxywebsite.mynote.entity.Menu;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface MenuMapper {
    Menu findMenuById(int id);
    //userid和parentid
//    @Cacheable(cacheNames = "menu", key = "#userId")
    List<Menu> findMenusByUserIdAndParentId(int userId, int parentId);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") //为了获得新增主键
    @Insert("insert into menu(name, user_id, parent_id) values(#{name}, #{userId}, #{parentId})")
    boolean addMenu(Menu menu);


//    @Update("update menu set name = #{newName} where id = #{menuId} ")
    boolean updateMenu(Menu menu);

    @Delete("delete from menu where id = #{menuId}")
    boolean deleteMenuById(int menuId);

    public static int rootMenuId = 1;
}
