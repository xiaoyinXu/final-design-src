package com.xxywebsite.mynote.mapper;

import com.xxywebsite.mynote.entity.NoteStatistics;

import java.time.LocalDateTime;
import java.util.List;

public interface NoteStatisticsMapper {
    NoteStatistics findNoteStatisticsById(int id);
    List<NoteStatistics> findNoteStatisticsByDateTimeAndDays(LocalDateTime beginTime, int days);
}
