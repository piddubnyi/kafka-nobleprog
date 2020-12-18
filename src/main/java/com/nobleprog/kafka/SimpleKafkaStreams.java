package com.nobleprog.kafka;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class SimpleKafkaStreams {

    public static void main(String[] args) {
        SpringApplication.run(SimpleKafkaStreams.class, args);
    }

    @Configuration
    public static class KafaStreamsConfig {

        @Bean
        public KStream<Long, String> dayAggregation(StreamsBuilder streamsBuilder) {
            final KStream<Long, String> inputStream = streamsBuilder.stream("my-topic");
            inputStream.merge(streamsBuilder.stream("my-other-topic"));
            inputStream.to("copy-topic");
            return inputStream;

        }
    }
}
