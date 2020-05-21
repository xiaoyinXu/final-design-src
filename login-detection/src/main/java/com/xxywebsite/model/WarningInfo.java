package com.xxywebsite.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarningInfo {
    // 发往 es 的报错信息, 恶意登录的用户信息格式如下   id:    count:    String begin  String end
    Long userId;
    Long count;

    LocalDateTime beginTime;
    LocalDateTime endTime;
}
