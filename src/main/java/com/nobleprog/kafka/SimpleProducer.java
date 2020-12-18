package com.nobleprog.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleProducer {

    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.setProperty("schema.registry.url", "http://localhost:8081");
        props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        List<String> users = Arrays.asList("user1", "user2", "user");
        Producer<String, User> producer = new KafkaProducer<>(props);
        while (true) {
            producer.send(new ProducerRecord<>(
                    "all-users",
                    users.get(ThreadLocalRandom.current().nextInt(users.size())),
                   User.newBuilder()
                       .setName(UUID.randomUUID().toString())
                       .setAge(ThreadLocalRandom.current().nextInt(99))
                       .build()
            )
            );
            Thread.sleep(1000);
        }
    }
}
