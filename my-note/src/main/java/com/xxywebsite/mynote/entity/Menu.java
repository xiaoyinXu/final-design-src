package com.xxywebsite.mynote.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Data
public class Menu implements Serializable {
    @Id
    private int id;
    private int parentId;
    private int userId;
    private String name;

    private List<Menu> menus;
    private List<Menu> notes;
}
