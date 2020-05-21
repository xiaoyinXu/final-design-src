package com.xxywebsite.mynote.util;

import com.alibaba.fastjson.JSONObject;

public class FastJsonUtils {
    public static <T> T copy(T source, Class<? extends T> clazz) {
        String s = JSONObject.toJSONString(source);
        return JSONObject.parseObject(s, clazz);
    }
}
