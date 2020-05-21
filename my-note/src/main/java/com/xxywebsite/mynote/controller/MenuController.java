package com.xxywebsite.mynote.controller;


import com.xxywebsite.mynote.bean.RespBean;
import com.xxywebsite.mynote.config.UserId;
import com.xxywebsite.mynote.entity.Menu;
import com.xxywebsite.mynote.entity.User;
import com.xxywebsite.mynote.service.MenuService;
import com.xxywebsite.mynote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class MenuController {
    @Autowired
    UserService userService;

    @Autowired
    MenuService menuService;

//    @Insert("insert into menu(name, user_id, parent_id) values(#{menuName}, #{userId}, #{parentId})")
//    Menu addMenu(String menuName, int userId, int parentId);
//
//    @Update("update menu where id = #{menuId} set name = #{newName}")
//    boolean modifyMenuName(int menuId, String newName);
//
//    @Delete("delete from menu where id = # {menuId}")
//    boolean deleteMenuById(int menuId);


    @DeleteMapping("/menus/{id}")
    public RespBean deleteMenuById(@PathVariable("id") int id) {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();
        String username = auth.getName();
        User user = userService.findUserByName(username);
        int userId = user.getId();

        if (menuService.deleteMenuById(id, userId)) {
            return RespBean.ok("删除成功");
        } else {
            return RespBean.error("删除失败");
        }
    }

    @PostMapping("/menus")
    public RespBean addMenu(@RequestBody Menu menu) {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();
        String username = auth.getName();
        User user = userService.findUserByName(username);
        int userId = user.getId();

        menu.setUserId(userId);
        if (menuService.addMenu(menu)) {
            return new RespBean(201,"添加成功", menu);
        } else {
            return RespBean.error("添加失败");
        }

    }

    @PutMapping("/menus/{id}")
    public RespBean updateMenu(@PathVariable("id") int id, @RequestBody Menu menu) {
        menu.setId(id);
        if (menuService.updateMenu(menu)) {
            return RespBean.ok("修改成功");
        } else {
            return RespBean.error("修改失败");
        }
    }

    @GetMapping("/menus")
    public RespBean menus(@UserId Integer userId) {
        //just for getting the userId
//        SecurityContext sc = SecurityContextHolder.getContext();
//        Authentication auth = sc.getAuthentication();
//        String username = auth.getName();
//        User user = userService.findUserByName(username);
//        int userId = user.getId();

        List<Menu> menus = menuService.findMenusByUserIdAndParentId(userId, 1);


        return RespBean.ok("成功", menus);
    }


}
