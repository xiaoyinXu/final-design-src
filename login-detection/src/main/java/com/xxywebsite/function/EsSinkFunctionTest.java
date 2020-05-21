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
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EsSinkFunctionTest {
    public static ElasticsearchSink<String> getEsSink(String host, int port, String index, String type) {
        Map<String, String> config = new HashMap<>();
        config.put("cluster.name", "docker-cluster");
        config.put("bulk.flush.max.actions", "1");

        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost(host, port));

        Builder<String> builder = new Builder<>(httpHosts, new ElasticsearchSinkFunction<String>() {
            public IndexRequest createIndexRequest(String element) {

                Map<String, Object> map = new HashMap<>();
                map = JSONObject.parseObject(JSONObject.toJSONString(element), new TypeReference<Map<String, Object>>() {
                });

                return Requests.indexRequest()
                        .index(index)
                        .type(type)
                        .source(map)
                        .create(true)
                        ;
            }

            @Override
            public void process(String element, RuntimeContext ctx, RequestIndexer indexer) {

                indexer.add(createIndexRequest(element));

            }
        });
        builder.setFailureHandler(new ActionRequestFailureHandler() {
            @Override
            public void onFailure(ActionRequest actionRequest, Throwable throwable, int i, RequestIndexer requestIndexer) throws Throwable {
                System.out.println("fail");
            }
        });

        ElasticsearchSink<String> esSink = builder.build();



        return esSink;
    }
}
