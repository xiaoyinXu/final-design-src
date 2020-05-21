package com.xxywebsite.mynote.util;

import com.google.gson.Gson;

public class GsonCopyUtils {
    public static <T> T gsonCopy(T source, Class<T> tClass) {


        // 使用Gson序列化进行深拷贝
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(source), tClass);



    }
}
