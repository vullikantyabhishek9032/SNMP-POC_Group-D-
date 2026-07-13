package com.hcl.Troy.Config;

import com.hcl.Troy.DTO.AlarmEvent;
import com.hcl.Troy.DTO.RecommendationDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, AlarmEvent> consumerFactory() {

        JsonDeserializer<AlarmEvent> deserializer =
                new JsonDeserializer<>(AlarmEvent.class);

        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "10.161.5.175:9092");

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
                "10.161.5.175:9092");

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
}