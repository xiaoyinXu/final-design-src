package com.xxywebsite.function;

import com.xxywebsite.model.NoteInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class MyKafkaConsumer {
    public static FlinkKafkaConsumer<NoteInfo> getKafkaConsumer(
            String bootstrapServers,
            String topic,
            String groupId) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", bootstrapServers);
        properties.setProperty("group.id", groupId);
        return new FlinkKafkaConsumer<NoteInfo>(topic, new KafkaDeserializationSchema<NoteInfo>() {
            @Override
            public boolean isEndOfStream(NoteInfo result) {
                return false;
            }

            @Override
            public NoteInfo deserialize(ConsumerRecord<byte[], byte[]> consumerRecord) throws Exception {
                // 1,61,access,2020-03-19 15:50:34
                // uesrId, noteId, behavior, timestamp
                byte[] value = consumerRecord.value();
                String valueString = new String(value);
                String[] words = valueString.split(",");
                Long userId = Long.valueOf(words[0].trim());
                Long noteId = Long.valueOf(words[1].trim());
                String behavior = words[2].trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                long timestamp = LocalDateTime.parse(words[3].trim(), formatter).toEpochSecond(ZoneOffset.ofHours(8));


                return new NoteInfo(userId, noteId, behavior, timestamp);
            }

            @Override
            public TypeInformation<NoteInfo> getProducedType() {
                return TypeInformation.of(NoteInfo.class);
            }
        },properties);


    }
}
