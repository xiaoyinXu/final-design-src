package com.xxywebsite.mynote.syntax;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTest {
    @Test
    public void test1() {
//        LocalDate
//        System.out.println(LocalDateTime.parse("2020-02-17T15:37:23"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm-ss");
        System.out.println(LocalTime.parse("23-59-59", formatter));
    }
}
