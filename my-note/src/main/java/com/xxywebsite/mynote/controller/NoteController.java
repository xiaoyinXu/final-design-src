package com.xxywebsite.mynote.controller;

import com.xxywebsite.mynote.bean.RespBean;
import com.xxywebsite.mynote.config.UserId;
import com.xxywebsite.mynote.entity.Note;
import com.xxywebsite.mynote.entity.User;
import com.xxywebsite.mynote.mapper.NoteMapper;
import com.xxywebsite.mynote.service.NoteService;
import com.xxywebsite.mynote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoteController {
    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    //add note
    @PostMapping("/notes")
    public RespBean addNote(@RequestBody Note note, @UserId Integer userId) {

        if (noteService.addNote(note, userId)) {
            return new RespBean(201, "添加成功", note);
        } else {
            return RespBean.error("添加失败");
        }
    }

    //delete note
    @DeleteMapping("/notes/{id}")
    public RespBean deleteNote(@PathVariable("id") int id, @UserId Integer userId) {
        if (noteService.deleteNote(id, userId)) {
            return RespBean.ok("删除成功");
        } else {
            return RespBean.error("删除失败");
        }
    }

    //update note
    @PutMapping("/notes/{id}")
    public RespBean updateNote(@PathVariable("id") int id, @RequestBody Note note, @UserId Integer userId) {
        note.setId(id);
        if (noteService.updateNote(note, userId)) {
            return RespBean.ok("修改成功");
        } else {
            return RespBean.error("修改失败");
        }
    }
}
