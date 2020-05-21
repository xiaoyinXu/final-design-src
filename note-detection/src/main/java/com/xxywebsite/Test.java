package com.xxywebsite;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {

        System.out.println("Hello World");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);  //
//
//        List<Integer> array = new ArrayList<>();
//        for (int i = 0; i < 10000000; i++) {
//            array.add(1);
//        }


        SingleOutputStreamOperator<Integer> result = env.socketTextStream("localhost",7777)
                .map(new MapFunction<String, Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> map(String value) throws Exception {
                        return new Tuple2<>("dummy", value);
                    }
                })
                .keyBy(p->p.f0)
                .timeWindow(Time.seconds(5))
                .trigger(new Trigger<Tuple2<String, String>, TimeWindow>() {
                    @Override
                    public TriggerResult onElement(Tuple2<String, String> element, long timestamp, TimeWindow window, TriggerContext ctx) throws Exception {
                        ctx.registerProcessingTimeTimer(window.getStart() + 3000);
                        ctx.registerProcessingTimeTimer(window.maxTimestamp() + 6000);
                        return TriggerResult.CONTINUE;
                    }

                    @Override
                    public TriggerResult onProcessingTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {
                        if (time == window.maxTimestamp()) {
                            return TriggerResult.FIRE_AND_PURGE;

                        } else {
                            return TriggerResult.CONTINUE;
                        }
                    }

                    @Override
                    public TriggerResult onEventTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {
//                        return TriggerResult.FIRE_AND_PURGE;
                        return null;
                    }

                    @Override
                    public void clear(TimeWindow window, TriggerContext ctx) throws Exception {

                    }
                })
                .process(new ProcessWindowFunction<Tuple2<String, String>, Integer, String, TimeWindow>() {
                    private Integer count = 0;

                    @Override
                    public void process(String s, Context context, Iterable<Tuple2<String, String>> elements, Collector<Integer> out) throws Exception {
                        for (Tuple2<String, String> val : elements) {
                            count++;

                        }
                        out.collect(count);

                    }
                });


        result.print("result");

        env.execute("job");

//        env.fromElements()
//                .keyBy()
//                .timeWindow()
//                .trigger()
//                .process()

//                .trigger()
//                .process(new ProcessWindowFunction<Object, Object, Tuple, TimeWindow>() {
//                    @Override
//                    public void process(Tuple tuple, Context context, Iterable<Object> elements, Collector<Object> out) throws Exception {
//
//                    }
//                })
//                .apply(new WindowFunction<Object, Object, Tuple, TimeWindow>() {
//                    @Override
//                    public void apply(Tuple tuple, TimeWindow window, Iterable<Object> input, Collector<Object> out) throws Exception {
//
//                    }
//                })
    }
}
