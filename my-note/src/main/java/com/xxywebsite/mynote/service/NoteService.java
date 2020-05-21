package com.xxywebsite.mynote.service;

import com.xxywebsite.mynote.entity.Note;
import com.xxywebsite.mynote.mapper.NoteMapper;
import com.xxywebsite.mynote.util.KafkaProducerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NoteService {
    @Autowired
    NoteMapper noteMapper;

    @Autowired
    EsNoteDetailsService esNoteDetailsService;

    // 增加日记要让menuService失效
    @CacheEvict(cacheNames = "menu", key = "#userId")
    public boolean addNote(Note note, int userId) {

        boolean success = noteMapper.addNote(note);
        int noteId = note.getId();
        String behavior = "add";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = LocalDateTime.now().format(formatter);
        String msg = String.format("%d,%d,%s,%s",userId, noteId, behavior, time);
        KafkaProducerUtils.send("note-info",msg);
        return success;
    }

    @CacheEvict(cacheNames = "menu", key = "#userId")
    public boolean deleteNote(int id, int userId) {
        esNoteDetailsService.deleteNote(id);
        boolean success = noteMapper.deleteNote(id);

        int noteId = id;
        String behavior = "delete";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = LocalDateTime.now().format(formatter);
        String msg = String.format("%d,%d,%s,%s",userId, noteId, behavior, time);
        KafkaProducerUtils.send("note-info",msg);


        return success;
    }

    @CacheEvict(cacheNames = "menu", key = "#userId")
    public boolean updateNote(Note note, int userId) {
        esNoteDetailsService.updateNoteName(note.getId(), note.getName());
        return noteMapper.updateNote(note);
    }

    public List<Note> findNotesByMenuId(int menuId) {
        return noteMapper.findNotesByMenuId(menuId);
    }
}
