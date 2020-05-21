package com.xxywebsite.mynote.service;

import com.xxywebsite.mynote.entity.NoteStatistics;
import com.xxywebsite.mynote.mapper.NoteStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteStatisticsService {

    @Autowired
    NoteStatisticsMapper noteStatisticsMapper;


    public List<NoteStatistics> findNoteStatisticsByDateTimeAndDays(LocalDateTime beginTime, int days) {
        return noteStatisticsMapper.findNoteStatisticsByDateTimeAndDays(beginTime, days);
    }
}
