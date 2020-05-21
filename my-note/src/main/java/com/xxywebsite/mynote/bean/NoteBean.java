package com.xxywebsite.mynote.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteBean {
    public String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate date;
}
