package com.xxywebsite.mynote.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerUtils {
    public static KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate2(KafkaTemplate<String, String> k) {
        kafkaTemplate = k;
    }


    public static void send(String topic, String msg) {
        kafkaTemplate.send(topic, msg);
    }
}
