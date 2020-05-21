package com.xxywebsite.mynote.mapper;

import com.xxywebsite.mynote.entity.Note;
import com.xxywebsite.mynote.entity.NoteStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class NoteStatisticsMapperTest {
    @Autowired
    NoteStatisticsMapper noteStatisticsMapper;


    @Test
    public void test1() {
        NoteStatistics noteStatisticsById = noteStatisticsMapper.findNoteStatisticsById(1);
        System.out.println(noteStatisticsById);
    }
    @Test
    public void test2() {
        LocalDateTime beginTime = LocalDateTime.of(2020, 3, 16, 0, 0, 0);
        List<NoteStatistics> noteStatisticsByDateTimeAndDays = noteStatisticsMapper.findNoteStatisticsByDateTimeAndDays(beginTime, 7);

        for (NoteStatistics s : noteStatisticsByDateTimeAndDays) {
            System.out.println(s);
        }
//        System.out.println(noteStatisticsByDateTimeAndDays);
    }
}