package com.xxywebsite.mynote.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface MyElasticsearchRepository extends ElasticsearchRepository<EsNote, Integer> {
    List<EsNote> findByNameOrContent(String msg, String msg2);
    List<EsNote> findByContent(String msg);
//    List<EsNote> findByCreateDateBetween(Date from, Date to);
}
