package com.xxywebsite.function;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xxywebsite.model.WarningInfo;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.connectors.elasticsearch.ActionRequestFailureHandler;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink.Builder;
import org.apache.flink.table.descriptors.Elasticsearch;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EsSinkFunction {
    public static ElasticsearchSink<WarningInfo> getEsSink(String host, int port, String index, String type) {
        Map<String, String> config = new HashMap<>();
        config.put("cluster.name", "docker-cluster");
        config.put("bulk.flush.max.actions", "1");

        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost(host, port));

        Builder<WarningInfo> builder = new Builder<>(httpHosts, new ElasticsearchSinkFunction<WarningInfo>() {
            public IndexRequest createIndexRequest(WarningInfo element) {

                Map<String, Object> map = new HashMap<>();
                map = JSONObject.parseObject(JSONObject.toJSONString(element), new TypeReference<Map<String, Object>>() {
                });

                return Requests.indexRequest()
                        .index(index)
                        .type(type)
                        .source(map);
            }

            @Override
            public void process(WarningInfo element, RuntimeContext ctx, RequestIndexer indexer) {

                indexer.add(createIndexRequest(element));

            }
        });
        builder.setFailureHandler(new ActionRequestFailureHandler() {
            @Override
            public void onFailure(ActionRequest actionRequest, Throwable throwable, int i, RequestIndexer requestIndexer) throws Throwable {
                System.out.println("fail");
            }
        });

        ElasticsearchSink<WarningInfo> esSink = builder.build();



        return esSink;
    }
}
