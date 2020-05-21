package com.xxywebsite.mynote.entity;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Note implements Serializable {
    private int id;
    private int menuId;
    private String name;
//    private String content;
//    private LocalDateTime createDate;

    // plus
    private Menu menu;
}
