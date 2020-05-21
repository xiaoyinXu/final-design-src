package com.xxywebsite.function;

import com.xxywebsite.model.NoteInfo;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

public class MyTriggerFunction extends Trigger<NoteInfo, TimeWindow> {
    @Override
    public TriggerResult onElement(NoteInfo element, long timestamp, TimeWindow window, TriggerContext ctx) throws Exception {
        // 设置定时器
//        ctx.registerEventTimeTimer(window.maxTimestamp());
//        return TriggerResult.CONTINUE;


        return TriggerResult.FIRE_AND_PURGE; // 来一条处理一条
    }

    @Override
    public TriggerResult onProcessingTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {

        return null;
    }

    @Override
    public TriggerResult onEventTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {
        //

//        return null;

        return TriggerResult.FIRE; // 这里有一点伏笔, 直接返回null会不会有问题?
    }

    @Override
    public void clear(TimeWindow window, TriggerContext ctx) throws Exception {

    }
}
