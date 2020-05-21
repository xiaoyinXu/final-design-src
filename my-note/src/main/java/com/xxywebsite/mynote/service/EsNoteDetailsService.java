package com.xxywebsite.mynote.service;

import com.xxywebsite.mynote.entity.NoteDetails;
import com.xxywebsite.mynote.es.EsNote;
import com.xxywebsite.mynote.es.MyElasticsearchRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class EsNoteDetailsService {
    @Autowired
    MyElasticsearchRepository repository;

    public List<EsNote> findNoteDetailsCustomized(String keyword, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        // (content or name ) and (date)
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("name", keyword))
                .should(QueryBuilders.matchQuery("content", keyword))
                .minimumShouldMatch(1)
                .must(QueryBuilders.rangeQuery("createDate").from(from.toString()).to(to.toString()));
        Iterable<EsNote> search = repository.search(query);
        Iterator<EsNote> iterator = search.iterator();
        List<EsNote> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;


    }
//    public void addNoteDetails(EsNote esNote) {
//        repository.save(esNote);
//    }
    public void updateNoteName(int noteId, String newName) {
        Optional<EsNote> byId = repository.findById(noteId);
        EsNote esNote = byId.get();
        esNote.setName(newName);
        repository.save(esNote);

    }
    public void updateNoteContent(int noteId, String newContent) {
        Optional<EsNote> byId = repository.findById(noteId);
        EsNote esNote = byId.get();
        esNote.setContent(newContent);
        repository.save(esNote);
    }

    public void deleteNote(Integer noteId) {
        repository.deleteById(noteId);
    }
    public void addNote(EsNote note) {
        repository.save(note);
    }
}
