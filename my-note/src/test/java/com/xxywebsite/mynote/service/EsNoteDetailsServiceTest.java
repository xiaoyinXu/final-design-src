package com.xxywebsite.mynote.service;

import com.xxywebsite.mynote.es.EsNote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class EsNoteDetailsServiceTest {
    @Autowired
    EsNoteDetailsService esNoteDetailsService;

    @Test
    public void test1() {
//        EsNote esNote = new EsNote(666, 666, "a", "b", LocalDate.now());
//        esNoteDetailsService.addNoteDetails(esNote);
        esNoteDetailsService.updateNoteName(666, "你好呀");
    }
    @Test
    public void updateNoteContentTest() {
        esNoteDetailsService.updateNoteContent(666,"Do what feels great");
    }
    @Test
    public void deleteNoteTest() {
        esNoteDetailsService.deleteNote(667);
    }
}