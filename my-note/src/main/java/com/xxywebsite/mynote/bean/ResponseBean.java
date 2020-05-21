package com.xxywebsite.mynote.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBean {

    private Object data;
    private Meta meta;
}
