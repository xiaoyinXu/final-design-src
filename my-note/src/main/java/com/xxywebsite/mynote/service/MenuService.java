package com.xxywebsite.mynote.service;

import com.xxywebsite.mynote.entity.Menu;
import com.xxywebsite.mynote.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "menu")
public class MenuService {
    @Autowired
    MenuMapper menuMapper;


    @Cacheable(key = "#userId")
    public List<Menu> findMenusByUserIdAndParentId(int userId, int parentId) {
        return menuMapper.findMenusByUserIdAndParentId(userId, parentId);
    }

    //    @Insert("insert into menu(name, user_id, parent_id) values(#{menuName}, #{userId}, #{parentId})")
    //增加日志要使menuSerivce:userId失效
    @CacheEvict(cacheNames = "menu", key = "#menu.userId")
    public boolean addMenu(Menu menu) {
        return menuMapper.addMenu(menu);
    }


    @CacheEvict(cacheNames = "menu", key = "#menu.userId")
//    @CachePut(key = "#menu.id")
//    @Update("update menu where id = #{menuId} set name = #{newName}")
    public boolean updateMenu(Menu menu) {
        return menuMapper.updateMenu(menu);
    }

    //    @Delete("delete from menu where id = # {menuId}")
    @CacheEvict(cacheNames = "menu", key = "#userId")
    public boolean deleteMenuById(int menuId, int userId) {
        return menuMapper.deleteMenuById(menuId);
    }

}
