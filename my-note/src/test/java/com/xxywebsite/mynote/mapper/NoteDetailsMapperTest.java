package com.xxywebsite.mynote.mapper;

import com.xxywebsite.mynote.entity.NoteDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoteDetailsMapperTest {
    @Autowired
    NoteDetailsMapper noteDetailsMapper;


    @Test
    public void test1() {
        noteDetailsMapper.findNoteDetailsById(1);
//        NoteDetails noteDetails = new NoteDetails(0, 3, "untitled2", "bala", LocalDate.now());
//        noteDetailsMapper.addNoteDetails(noteDetails);

//        noteDetails.setContent("untitled");
//        noteDetailsMapper.updateNoteDetails(noteDetails);

//        noteDetailsMapper.deleteNoteDetails(noteDetails.getId());
    }

}