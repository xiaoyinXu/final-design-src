package com.xxywebsite.mynote.es;

import com.xxywebsite.mynote.bean.WarningInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EsLoginWarningRepository extends ElasticsearchRepository<WarningInfo,String> {
//    List<WarningInfo> findByUserId(Long userId);
    List<WarningInfo> findByCount(Long count);
}
