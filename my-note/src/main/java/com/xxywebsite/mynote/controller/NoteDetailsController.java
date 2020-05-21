package com.xxywebsite.mynote.controller;

import com.xxywebsite.mynote.bean.NoteQueryBean;
import com.xxywebsite.mynote.bean.RespBean;
import com.xxywebsite.mynote.config.UserId;
import com.xxywebsite.mynote.entity.NoteDetails;
import com.xxywebsite.mynote.es.EsNote;
import com.xxywebsite.mynote.service.EsNoteDetailsService;
import com.xxywebsite.mynote.service.NoteDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class NoteDetailsController {
    @Autowired
    NoteDetailsService noteDetailsService;




    @Autowired
    EsNoteDetailsService esNoteDetailsService;

    @PostMapping("/notes_details")
    public RespBean addNoteDetails(@RequestBody NoteDetails noteDetails, @UserId Integer userId) {
        if (noteDetailsService.addNoteDetails(noteDetails, userId)) {
            return new RespBean(201, "添加成功", noteDetails);
        } else {
            return RespBean.error("添加失败");
        }
    }

    @DeleteMapping("/notes_details/{id}")
    public RespBean deleteNoteDetails(@PathVariable("id") int id, @UserId Integer userId) {
        if (noteDetailsService.deleteNoteDetails(id, userId)) {
            return RespBean.ok("删除成功");
        } else {
            return RespBean.error("删除失败");
        }
    }

    @PutMapping("/notes_details/{id}")
    public RespBean updateNoteDetails(@PathVariable("id") int id, @RequestBody NoteDetails noteDetails, @UserId Integer userId) {
        noteDetails.setId(id);
        if (noteDetailsService.updateNoteDetails(noteDetails, userId)) {
            return RespBean.ok("修改成功");
        } else {
            return RespBean.error("修改失败");
        }
    }

    @GetMapping("/notes_details/{id}")
    public RespBean getNoteDetailsById(@PathVariable("id") int id, @UserId Integer userId) {
        NoteDetails noteDetails = null;
        if ((noteDetails = noteDetailsService.findNoteDetailsById(id, userId)) != null) {
            return RespBean.ok("查询成功", noteDetails);
        } else {
            return RespBean.error("查询失败");
        }
    }

    @GetMapping("/notes_details")
    public RespBean getNoteDetailsByQueryBean(String keyword,
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(defaultValue = "0000-01-01") LocalDate from,
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(defaultValue = "9999-12-31" ) LocalDate to) {
//        return RespBean.ok("123");
//        NoteQueryBean noteQueryBean = new NoteQueryBean(keyword, from, to);
//        List<NoteDetails> result = noteDetailsService.findNoteDetailsByNoteQueryBean(noteQueryBean);
        List<EsNote> result = esNoteDetailsService.findNoteDetailsCustomized(keyword, from, to);
        if (result != null) {
            return RespBean.ok("获取成功", result);
        } else {
            return RespBean.error("获取失败");
        }


    }
}
