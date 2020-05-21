package com.xxywebsite.mynote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoteDetails implements Serializable {
    @Id
    private int id;
    private int menuId;
    private String name;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;


    // plus
    Menu menu;
}
