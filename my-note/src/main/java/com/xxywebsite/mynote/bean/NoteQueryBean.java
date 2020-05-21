package com.xxywebsite.mynote.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteQueryBean {
    String keyword;
    @DateTimeFormat(pattern = "yyyy HH dd")
    LocalDate from;
//    @DateTimeFormat(pattern = "yyyy-HH-dd")
    LocalDate to;


}
