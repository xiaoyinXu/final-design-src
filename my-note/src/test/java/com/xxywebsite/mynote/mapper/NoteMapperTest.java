package com.xxywebsite.mynote.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoteMapperTest {
    @Autowired
    NoteMapper noteMapper;

    @Test
    public void test1 () {
//        System.out.println(noteMapper.findNoteById(1));
        System.out.println(noteMapper.findNotesByMenuId(3));
    }
}