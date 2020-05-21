package com.xxywebsite;

import com.xxywebsite.function.EsSinkFunction;
import com.xxywebsite.function.EsSinkFunctionTest;
import com.xxywebsite.function.KafkaSourceFunction;
import com.xxywebsite.function.MySQLSinkFunction;
import com.xxywebsite.model.LoginInfo;
import com.xxywebsite.model.WarningInfo;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.nfa.aftermatch.AfterMatchSkipStrategy;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class LoginDetection {
    public static final Long SECONDS = 10L;
    public static final int COUNTS = 3;


    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // source
        DataStream<LoginInfo> source = env
//                .fromElements(
//                        new LoginInfo(1L,"fail",1000000000L)
//                        ,new LoginInfo(1L,"fail",1000000001L)
//                        ,new LoginInfo(1L,"fail",1000000002L))
                .addSource(new KafkaSourceFunction())
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<LoginInfo>(Time.seconds(0)) {
                    @Override
                    public long extractTimestamp(LoginInfo element) {
                        return element.getTimestamp() * 1000L;
                    }
                });

        KeyedStream<LoginInfo, Long> keyedStream = source.keyBy(new KeySelector<LoginInfo, Long>() {
            @Override
            public Long getKey(LoginInfo value) throws Exception {
                return value.getId();
            }
        });

        // test
        // transform
        // 利用CEP 检测出10秒内连续登录5次的用户
        Pattern<LoginInfo, LoginInfo> pattern = Pattern.<LoginInfo>begin("begin")
                .where(new IterativeCondition<LoginInfo>() {
                    @Override
                    public boolean filter(LoginInfo value, Context<LoginInfo> ctx) throws Exception {
                        return "fail".equals(value.getBehavior());
                    }
                })
                .timesOrMore(COUNTS)
                .within(Time.seconds(SECONDS));
        PatternStream<LoginInfo> patternStream = CEP.pattern(keyedStream, pattern);
        DataStream<WarningInfo> result = patternStream.select(new PatternSelectFunction<LoginInfo, WarningInfo>() {
            @Override
            public WarningInfo select(Map<String, List<LoginInfo>> pattern) throws Exception {
                List<LoginInfo> infos = pattern.get("begin");
                //{id:1, count:10,  beginTime,  endTime}
                LoginInfo firstFailLoginInfo = infos.get(0);
                Long id = firstFailLoginInfo.getId();

                // infos的长度
                Long count = Long.valueOf(infos.size());
                Long firstFailTimeStamp = firstFailLoginInfo.getTimestamp();
                Long endTimeStamp = firstFailTimeStamp + SECONDS;

                // 将timeStamp转换成时间
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime beginTime = LocalDateTime.ofEpochSecond(firstFailTimeStamp, 0, ZoneOffset.ofHours(8));
                LocalDateTime endTime = LocalDateTime.ofEpochSecond(endTimeStamp, 0, ZoneOffset.ofHours(8));

                return new WarningInfo(id, count, beginTime, endTime);
            }
        });



        // sink
        source.print("source");
        result.print("result");
//        result.addSink(EsSinkFunction.getEsSink("localhost", 9200, "login-warning4","abc"));
        result.addSink(new MySQLSinkFunction());

        // test
//        env.fromElements(new WarningInfo(2L,5L,"2020-03-15 15:00:00","2020-03-15 15:00:10"))
//                .addSink(EsSinkFunction.getEsSink("localhost", 9200, "login-warning3","_doc"));
//
//        result.addSink(EsSinkFunction.getEsSink("localhost", 9200, "login-warning3","_doc"));



        env.execute("job");
    }
}
