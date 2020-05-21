package com.xxywebsite.mynote.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "login-warning", type = "_doc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarningInfo {
//    @Field(name = "_id")
    @Id
    String id;

    @Field(type = FieldType.Long)
    Long userId;

    @Field(type = FieldType.Long)
    Long count;

    @Field(type = FieldType.Text)
    String beginTime;

    @Field(type = FieldType.Text)
    String endTime;
}
