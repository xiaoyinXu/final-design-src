package com.xxywebsite.util;

public class BloomFilterUtils {

    // 根据Long 得到一个offset
    public static Long getOffset(Long cap, Long key) {
        return key % cap;
    }

}
