package com.xxywebsite.mynote.controller;


import com.xxywebsite.mynote.bean.*;
import com.xxywebsite.mynote.entity.NoteDetails;
import com.xxywebsite.mynote.es.EsNote;
import com.xxywebsite.mynote.es.MyElasticsearchRepository;
import com.xxywebsite.mynote.service.EsNoteDetailsService;
import com.xxywebsite.mynote.service.NoteDetailsService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.*;

@RestController
@RequestMapping("/test")
@CrossOrigin

public class TestController {
    @Autowired
    EsNoteDetailsService esNoteDetailsService;

    @Autowired
    NoteDetailsService noteDetailsService;

    @Autowired
    MyElasticsearchRepository repository;

    @GetMapping("/hello")
    public String hello() {
        //插入数据
//        EsNote esNote = new EsNote(2, 2, "模式识别", "机器学习的一个分支", new Date());
//        repository.save(esNote);

        // (name 或 content里面有模式) 并且 menuId = 2
        //(name or content) and (date)
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("content", "模式"))
                .should(QueryBuilders.matchQuery("name", "模式"))
                .minimumShouldMatch(1);


        Iterable<EsNote> search = repository.search(query);


        return "Hello From Cookie~";
    }

    @GetMapping("/today")
    public LocalDate localDate() {
        return LocalDate.now();
    }

    //    @PostMapping("/date")
    @RequestMapping(value = "/date")
    public String date(NoteBean bean, HttpServletRequest request, String name) {
        return "123";
    }

    @RequestMapping(value = "/response")
    public ResponseBean date() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Cookie");
        map.put("age", 22);
        HashMap hashMap = new HashMap();
        return new ResponseBean(map, new Meta(200, "成功"));
    }

    @PostMapping("/date2")
    public String date(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        return "123";
    }

    @PostMapping("/date3")
    public String date3(@DateTimeFormat(pattern = "yyyy-MM-ddT*") LocalDate date) {
        return "123";
    }


    @GetMapping("/notes_details")
    public List<NoteDetails> findNotesBySearchBean(String keyword, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        NoteQueryBean noteQueryBean = new NoteQueryBean(keyword, from, to);
        List<NoteDetails> list = noteDetailsService.findNoteDetailsByNoteQueryBean(noteQueryBean);
        return list;
    }

    @GetMapping("/notes_details2")
    public List<NoteDetails> findNotesBySearchBean2(@RequestBody @DateTimeFormat(pattern = "yyyy HH dd") NoteQueryBean bean) {


        return new ArrayList<>();
    }

    @GetMapping("/notes_details3")
    // 用户空传
    public RespBean findNotesCustomized(String keyword, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(defaultValue = "0000-01-01") LocalDate from, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(defaultValue = "9999-12-31") LocalDate to) {
        List<EsNote> result = esNoteDetailsService.findNoteDetailsCustomized(keyword, from, to);
        if (result != null) {
            return RespBean.ok("获取成功", result);
        } else {
            return RespBean.error("获取失败");
        }

    }

    @GetMapping("/estest")
    public String estest(String keyword) {
        List<EsNote> byContent = repository.findByContent(keyword);
        return "123";
    }

    @GetMapping("/status")
    public RespBean statsTest() {
        return RespBean.error("error");
    }
}
