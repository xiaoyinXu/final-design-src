package com.xxywebsite.mynote.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONCopyUtils {
    private static ObjectMapper mapper = new ObjectMapper();


    public static String encodeWithoutNull(Object obj) throws Exception {
        return mapper.writeValueAsString(obj);
    }

    public static <T> T decodeValueIgnoreUnknown(String str, Class<T> clazz) throws Exception {
        return mapper.readValue(str, clazz);
    }


    // 一千万次  15.3秒
    public static <T> T copy(T source, Class<T> tClass) throws Exception {
        return decodeValueIgnoreUnknown(encodeWithoutNull(source), tClass);
    }
}
