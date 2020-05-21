package com.xxywebsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteStatistics {
    // result
    // userId, beginTime, beginEnd, count
    Long userId;
    LocalDateTime beginTime;
    LocalDateTime endTime;
    Long count;
}
