package com.xxywebsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteInfo {
    // userId, noteId,  behavior,  timestamp
    Long userId;
    Long noteId;
    String behavior;
    Long timestamp;

}
