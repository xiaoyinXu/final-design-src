package com.xxywebsite.mynote.util;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EsUtils {
    public static final String INDEX = "login-warning";
    public static final String TYPE = "_doc";
    private static ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    void init(ElasticsearchTemplate elasticsearchTemplate) {
        EsUtils.elasticsearchTemplate = elasticsearchTemplate;
    }
    // 根据id 获取WarningInfo信息
    public static Map<String, Object> findWarningByUserId(int userId) {
        IndexQuery build = new IndexQueryBuilder()
                .withIndexName(INDEX)
                .withType(TYPE)

                .build()
                ;
        BoolQueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .must(QueryBuilders.matchQuery("id", userId))
                ;
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withIndices(INDEX)
                .withTypes(TYPE)
                .build();
        elasticsearchTemplate.query(query, new ResultsExtractor<Object>() {
            @Override
            public Object extract(SearchResponse response) {

                return response.getAggregations();
            }
        });
//        elasticsearchTemplate.query()
        return new HashMap<>();
    }
}
