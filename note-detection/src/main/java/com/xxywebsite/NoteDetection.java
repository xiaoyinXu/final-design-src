package com.xxywebsite;

import com.xxywebsite.function.MyKafkaConsumer;
import com.xxywebsite.function.MyProcessWindowFunction;
import com.xxywebsite.function.MySQLSinkFunction;
import com.xxywebsite.function.MyTriggerFunction;
import com.xxywebsite.model.NoteInfo;
import com.xxywebsite.model.NoteStatistics;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

public class NoteDetection {
    public static final String BOOTSTRAP_SERVERS = "mykafka:9092";
    public static final String TOPIC = "note-info";
    public static final String GROUP_ID = "default";

    public static void main(String[] args) throws Exception {


        // 从kakfa读取数据,  统计一小时内访问的日记总数(去重)

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // source
//        SingleOutputStreamOperator<NoteInfo> source = env.fromElements(
//                new NoteInfo(1L, 1L, "add", 1000000000L),
//                new NoteInfo(1L, 2L, "add", 1000000000L),
//                new NoteInfo(1L, 1L, "delete", 1000000000L)
//        env.addSource(MyKafkaConsumer.getKafkaConsumer("1","1","1"));
        SingleOutputStreamOperator<NoteInfo> source = env
                .addSource(MyKafkaConsumer.getKafkaConsumer(BOOTSTRAP_SERVERS, TOPIC, GROUP_ID))
//                .socketTextStream("localhost", 7777)
//                .map(new MapFunction<String, NoteInfo>() {
//                    @Override
//                    public NoteInfo map(String value) throws Exception {
//                        // 数据格式为
//                        // 1,123,add,2020-03-17 14:19:00
//                        String[] words = value.split(",");
//                        String timeString = words[words.length - 1].trim();
//                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                        LocalDateTime parsedTime = LocalDateTime.parse(timeString, formatter);
//                        Long timestamp = parsedTime.toEpochSecond(ZoneOffset.ofHours(8));
//                        Long userId = Long.valueOf(words[0].trim());
//                        Long noteId = Long.valueOf(words[1].trim());
//                        String behavior = words[2].trim();
//                        return new NoteInfo(userId, noteId, behavior, timestamp);
//                    }
//                })
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<NoteInfo>(Time.seconds(0)) {
                    @Override
                    public long extractTimestamp(NoteInfo element) {
                        return element.getTimestamp() * 1000L;
                    }
                });

        // transform
        // 统计一小时内每个用户的浏览的日记总数
        SingleOutputStreamOperator<NoteStatistics> result = source
                .keyBy(new KeySelector<NoteInfo, Long>() {
                    @Override
                    public Long getKey(NoteInfo value) throws Exception {
                        return value.getUserId();
                    }
                })
                .window(TumblingEventTimeWindows.of(Time.days(1), Time.hours(-8)))
                .allowedLateness(Time.seconds(1))
                .trigger(new MyTriggerFunction())
                .process(new MyProcessWindowFunction());


        // sink
        source.print("source");
        result.print("result");
        result.addSink(new MySQLSinkFunction());

        env.execute("job");

    }
}
