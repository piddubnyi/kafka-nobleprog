package com.nobleprog.kafka;

import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableKafkaStreams
public class SimpleKafkaStreams {

    public static void main(String[] args) {
        SpringApplication.run(SimpleKafkaStreams.class, args);
    }

    @Configuration
    public static class KafaStreamsConfig {


        @Bean
        public KStream<String, String> dayAggregation(StreamsBuilder streamsBuilder) {
            final KStream<String, String> inputStream = streamsBuilder.stream("my-topic");
            final KStream<String, String> mergedStream = inputStream.merge(streamsBuilder.stream("my-other-topic"));
            mergedStream.to("copy-topic");
            return mergedStream;
        }
    }

    @Component
    public static class KafkaStreamsHealthIndicator extends AbstractHealthIndicator {

        private StreamsBuilderFactoryBean streams;

        public KafkaStreamsHealthIndicator(StreamsBuilderFactoryBean streams) {
            super("KafkaStreams health check failed");
            this.streams = streams;
        }

        @Override
        protected void doHealthCheck(Health.Builder builder) {
            final KafkaStreams.State state = streams.getKafkaStreams().state();
            switch (state) {
                case ERROR:
                case PENDING_SHUTDOWN:
                case NOT_RUNNING:
                    builder
                        .down()
                        .withDetail("streams-state", state);
                    break;
                default:
                    builder.up();
            }
        }
    }
}
