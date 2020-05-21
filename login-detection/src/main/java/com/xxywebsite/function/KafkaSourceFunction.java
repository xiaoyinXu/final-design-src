package com.xxywebsite.function;

import com.xxywebsite.model.LoginInfo;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class KafkaSourceFunction extends FlinkKafkaConsumer<LoginInfo> {
    public static final String TOPIC = "login-info";
    public static final String BOOTSTRAP_SERVER = "mykafka:9092";
    public static final String GROUP_ID = "default";
    public static Properties properties;
    static {
        properties = new Properties();
        properties.setProperty("bootstrap.servers",BOOTSTRAP_SERVER);
        properties.setProperty("group.id", GROUP_ID);
    }


    public KafkaSourceFunction() {
        super(TOPIC, new KafkaDeserializationSchema<LoginInfo>() {
            @Override
            public boolean isEndOfStream(LoginInfo loginInfo) {
                return false;
            }

            @Override
            public LoginInfo deserialize(ConsumerRecord<byte[], byte[]> consumerRecord) throws Exception {
                //1,success,2020-03-15 14:26:00
                byte[] bytes = consumerRecord.value();
                String value = new String(bytes);
                String[] words = value.split(",");
                Long id = Long.valueOf(words[0].trim());
                String behavior = words[1].trim();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String timeString = words[2].trim();
                long timestamp = LocalDateTime.parse(timeString, formatter).toEpochSecond(ZoneOffset.ofHours(8));

                return new LoginInfo(id, behavior, timestamp);
            }

            @Override
            public TypeInformation<LoginInfo> getProducedType() {
                return TypeInformation.of(LoginInfo.class);
            }
        }, properties);
    }
}
