package com.xxywebsite.mynote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteStatistics {
    Integer id;
    Long userId;
    Long count;
    String behavior;
    LocalDateTime beginTime;
    LocalDateTime endTime;



}
