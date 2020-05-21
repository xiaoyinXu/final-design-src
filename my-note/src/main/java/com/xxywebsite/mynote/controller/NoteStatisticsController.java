package com.xxywebsite.mynote.controller;

import com.xxywebsite.mynote.bean.RespBean;
import com.xxywebsite.mynote.config.UserId;
import com.xxywebsite.mynote.entity.NoteStatistics;
import com.xxywebsite.mynote.service.NoteStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NoteStatisticsController {
    @Autowired
    NoteStatisticsService noteStatisticsService;


    @GetMapping("/note_statistics_last_week")
    public RespBean GetNoteStatisticsOfLastWeek(@UserId int userId, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime localDateTime) {
        // 假如时间为2020-03-21 16:40:00
        // 首先判断当前星期几;
        // 减去 7 + offset  获得上周第一天的日期+时间
        // 新建一个LocalDateTime, 抹去上一步的时间信息(00:00:00)

        int ordinal = localDateTime.getDayOfWeek().ordinal();
        LocalDateTime tempDateTime = localDateTime.minusDays(7 + ordinal);
        LocalDateTime dateTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
        LocalDateTime argument = dateTime
                .withYear(tempDateTime.getYear())
                .withMonth(tempDateTime.getMonth().ordinal())
                .withDayOfYear(tempDateTime.getDayOfYear());
        List<NoteStatistics> tempResult = noteStatisticsService.findNoteStatisticsByDateTimeAndDays(argument, 7);
        List<NoteStatistics> result = new ArrayList<>();

        // -1 -7
        for (int i = 0; i < 7; i++) {
            // -i, user_id, 0, null, localDateTime + i, localDateTime + (i + 1)
            result.add(new NoteStatistics(-i, Long.valueOf(userId), 0L, null, argument.plusDays(i), argument.plusDays(i + 1)));
        }

        for (int i = 0; i < tempResult.size(); i++) {
            NoteStatistics noteStatistics = tempResult.get(i);

            // 查看note... 与 localDateTime相差几
            long offset = ChronoUnit.DAYS.between(argument, noteStatistics.getBeginTime());
            result.set(Long.valueOf(offset).intValue(), noteStatistics);

        }


        return RespBean.ok("获取成功", result);


    }

    @GetMapping("/note_statistics_this_week")
    public RespBean GetNoteStatisticsOfThisWeek(@UserId int userId, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime localDateTime) {
        // 假如时间为2020-03-21 16:40:00
        // 首先判断当前星期几;
        // 减去 7 + offset  获得上周第一天的日期+时间
        // 新建一个LocalDateTime, 抹去上一步的时间信息(00:00:00)

        // 下面的逻辑最好放在service里, 懒得修改了
        int ordinal = localDateTime.getDayOfWeek().ordinal();
        LocalDateTime tempDateTime = localDateTime.minusDays(ordinal);
        LocalDateTime dateTime = LocalDateTime.of(1, 1, 1, 0, 0, 0);
        LocalDateTime argument = dateTime
                .withYear(tempDateTime.getYear())
                .withMonth(tempDateTime.getMonth().ordinal())
                .withDayOfYear(tempDateTime.getDayOfYear());
        List<NoteStatistics> tempResult = noteStatisticsService.findNoteStatisticsByDateTimeAndDays(argument, 7);
        List<NoteStatistics> result = new ArrayList<>();

        // -1 -7
        for (int i = 0; i < 7; i++) {
            // -i, user_id, 0, null, localDateTime + i, localDateTime + (i + 1)
            result.add(new NoteStatistics(-i, Long.valueOf(userId), 0L, null, argument.plusDays(i), argument.plusDays(i + 1)));
        }

        for (int i = 0; i < tempResult.size(); i++) {
            NoteStatistics noteStatistics = tempResult.get(i);

            // 查看note... 与 localDateTime相差几
            long offset = ChronoUnit.DAYS.between(argument, noteStatistics.getBeginTime());
            result.set(Long.valueOf(offset).intValue(), noteStatistics);

        }

        return RespBean.ok("获取成功", result);


    }
}
