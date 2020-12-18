package com.nobleprog.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class SimpleConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("schema.registry.url", "http://localhost:8081");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        KafkaConsumer<String, User> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("adult-users"));
        while (true) {
            ConsumerRecords<String, User> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, User> record : records) {
                final User user = record.value();
                System.out.printf("key = %s, value = %s%s", record.offset(), record.key(), user.getName()+user.getAge());
            }
        }
    }
}
