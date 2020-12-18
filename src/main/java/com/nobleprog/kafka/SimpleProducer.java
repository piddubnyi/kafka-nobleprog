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
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        List<String> users = Arrays.asList("user1-other", "user2-other", "user-other");
        List<String> updates = List.of("phone", "email", "status");
        Producer<String, String> producer = new KafkaProducer<>(props);
        while (true) {
            producer.send(new ProducerRecord<>(
                    "my-other-topic",
                    users.get(ThreadLocalRandom.current().nextInt(users.size())),
                    "{\"update\" : \"" +
                            updates.get(ThreadLocalRandom.current().nextInt(updates.size())) +
                            "\", \"value\": \""
                            + UUID.randomUUID().toString() +
            "\"}"
            )
            );
            Thread.sleep(1000);
        }
    }
}
