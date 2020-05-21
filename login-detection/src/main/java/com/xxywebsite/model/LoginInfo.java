package com.xxywebsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
    // kafka那边的消息是字符串  1,success,2020-03-15 14:13:00
    Long id;
    String behavior;
    Long timestamp;
}
