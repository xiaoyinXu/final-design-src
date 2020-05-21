package com.xxywebsite;

import com.xxywebsite.function.MySQLSinkFunction;
import com.xxywebsite.model.WarningInfo;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class MySQLSinkTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

//        DataStreamSource<WarningInfo> source = env.fromElements(new WarningInfo(1L, 5L, "2020-02-13", "2020-02-14"));


//        source.print("result");
//        source.addSink(new MySQLSinkFunction());

        env.execute("job");

    }
}
