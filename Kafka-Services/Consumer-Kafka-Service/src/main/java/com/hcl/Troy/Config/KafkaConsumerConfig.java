package com.hcl.Troy.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.Troy.DTO.AlarmEvent;
import com.hcl.Troy.DTO.RecommendationDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;
import org.springframework.kafka.core.*;

import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, AlarmEvent> consumerFactory() {

        JsonDeserializer<AlarmEvent> deserializer =
                new JsonDeserializer<>(AlarmEvent.class);

        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");

        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                "alarm-group");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AlarmEvent>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, AlarmEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
     @Bean
    public ConsumerFactory<String, RecommendationDTO> consumerFactoryRecommendation() {

        JsonDeserializer<RecommendationDTO> deserializer =
                new JsonDeserializer<>(RecommendationDTO.class);

        deserializer.addTrustedPackages("*");
        deserializer.ignoreTypeHeaders();
        Map<String, Object> props = new HashMap<>();

        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");

        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "metrics-group");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }


    @Bean(name = "kafkaListenerContainerRecommendationFactory")
    public ConcurrentKafkaListenerContainerFactory<String, RecommendationDTO>
    kafkaListenerContainerFactory1() {

        ConcurrentKafkaListenerContainerFactory<String, RecommendationDTO>
                factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactoryRecommendation());

        return factory;
    }



    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration streamsConfig() {

        Map<String, Object> props = new HashMap<>();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "recommendation-streams");

        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public KStream<String, RecommendationDTO> recommendationStream(StreamsBuilder builder) {

        ObjectMapper objectMapper = new ObjectMapper();

        return builder.stream("recommendation-topic",
                        Consumed.with(
                                Serdes.String(),
                                Serdes.String()
                        )
                )
                .mapValues(value -> {
                    try {
                        return objectMapper.readValue(value, RecommendationDTO.class);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }




    @Bean
    public StreamsBuilderFactoryBeanConfigurer configurer() {
        return factoryBean -> {
            factoryBean.setStateListener((newState, oldState) -> {
                System.out.println("STREAM STATE CHANGED: " + oldState + " -> " + newState);
            });

            factoryBean.setStreamsUncaughtExceptionHandler(
                    exception -> {
                        exception.printStackTrace();
                        return StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.SHUTDOWN_APPLICATION;
                    });
        };
    }


    @Bean
    public KTable<String, RecommendationDTO> recommendationTable(StreamsBuilder builder) {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonSerde<RecommendationDTO> recommendationSerde = new JsonSerde<>(RecommendationDTO.class);

        return builder
                .stream(
                        "recommendation-topic",
                        Consumed.with(
                                Serdes.String(),
                                Serdes.String()
                        )
                )
                .mapValues(value -> {
                    try {
                        return objectMapper.readValue(
                                value,
                                RecommendationDTO.class
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .selectKey((k, v) -> v.getCustomerId())
                .toTable(Materialized.<String, RecommendationDTO, KeyValueStore<Bytes, byte[]>>as("recommendation-store")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(recommendationSerde));
    }
}