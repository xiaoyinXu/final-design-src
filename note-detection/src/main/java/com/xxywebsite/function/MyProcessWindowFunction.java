package com.xxywebsite.function;

import com.xxywebsite.model.NoteInfo;
import com.xxywebsite.model.NoteStatistics;
import com.xxywebsite.util.BloomFilterUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MyProcessWindowFunction extends ProcessWindowFunction<NoteInfo, NoteStatistics, Long, TimeWindow> {

    Jedis jedis;
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        jedis = new Jedis("myredis", 6379);

    }

    @Override
    public void close() throws Exception {
        super.close();
        jedis.close();
    }

    @Override
    public void process(Long key, Context context, Iterable<NoteInfo> elements, Collector<NoteStatistics> out) throws Exception {
        // 首先获得key
        // 根据bloom过滤器得出offset

        // 构造redis-key
        // bitmap----userId:窗口end

        long begin = context.window().getStart() / 1000;
        long end = context.window().getEnd() / 1000;

//        String redisKey = "bitmap";
        String redisKey = String.format("%d:%d", key, end);

        Long offset = BloomFilterUtils.getOffset(1L << 29, elements.iterator().next().getNoteId()); // 理论上2^29个元素 16M


        if (!jedis.getbit(redisKey, offset)) {// 如果当前位置不存在
            jedis.setbit(redisKey, offset, true);
            
        } else {
            // do nothing,  我们假定hash算法非常好,  几乎不可能发生碰撞
        }

        if (true || context.currentWatermark() >= context.window().maxTimestamp()) {
            // 构造LocalDateTime
            LocalDateTime beginTime = LocalDateTime.ofEpochSecond(begin, 0, ZoneOffset.ofHours(8));
            LocalDateTime endTime = LocalDateTime.ofEpochSecond(end, 0, ZoneOffset.ofHours(8));
            out.collect(new NoteStatistics(key,beginTime, endTime, jedis.bitcount(redisKey)));
        }
    }
}
