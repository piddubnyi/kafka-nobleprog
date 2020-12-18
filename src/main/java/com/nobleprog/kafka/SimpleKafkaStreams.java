package com.nobleprog.kafka;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
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
        public KStream<String, User> dayAggregation(StreamsBuilder streamsBuilder) {
            final KStream<String, User> stream = streamsBuilder.stream("all-users");
            stream.filter((key, user) -> user.getAge() > 18)
                .to("adult-users");
            return stream;
        }

        @Bean
        public Serde<Object> user(KafkaAvroSerializer serializer, KafkaAvroDeserializer deserializer) {
            return new Serde<>() {
                @Override
                public Serializer<Object> serializer() {
                    return serializer;
                }

                @Override
                public Deserializer<Object> deserializer() {
                    return deserializer;
                }
            };
        }

        @Bean
        public KafkaAvroSerializer serializer(SchemaRegistryClient client){
            return new KafkaAvroSerializer(client);
        }

        @Bean
        public KafkaAvroDeserializer deserializer(SchemaRegistryClient client){
            return new KafkaAvroDeserializer(client);
        }

        @Bean
        public SchemaRegistryClient client(){
            return new CachedSchemaRegistryClient("http://localhost:8081", 10);
        }
    }
}
