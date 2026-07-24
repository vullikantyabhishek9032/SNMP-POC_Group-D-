package com.hcl.Troy;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hcl.Troy.DTO.*;
import com.hcl.Troy.Service.EmailService;
import com.hcl.Troy.Service.PlanService;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    private static  final Logger log = LoggerFactory.getLogger(KafkaConfig.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private PlanService planService;

    @Autowired
    private EmailService emailService;
    @Bean
    public ProducerFactory<String, MonitoringEvent> producerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.161.5.175:9092");

        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        JsonSerializer<MonitoringEvent> serializer = new JsonSerializer<>(mapper);

        return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), serializer);
    }

    @Bean
    public KafkaTemplate<String, MonitoringEvent> kafkaTemplate() {

        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, AlertEvent> producerAlertFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.161.5.175:9092");

        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, AlertEvent> kafkaAlertTemplate() {

        return new KafkaTemplate<>(producerAlertFactory());
    }

    @Bean
    public ProducerFactory<String, CustomerUsage> producerCustoemrFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.161.5.175:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }


    @Bean
    @Primary
    public KafkaTemplate<String, CustomerUsage> kafkaCustomerTemplate() {

        return new KafkaTemplate<>(producerCustoemrFactory());
    }

    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

    @Bean
    public ProducerFactory<String, SnmpTrapDTO> producerTrapFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.161.5.175:9092");

        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, SnmpTrapDTO> kafkaTrapTemplate() {

        return new KafkaTemplate<>(producerTrapFactory());
    }

    @Bean
    public ProducerFactory<String, TrapVarbindDTO> producerVarbindFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.161.5.175:9092");

        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, TrapVarbindDTO> kafkaVarbindTemplate() {

        return new KafkaTemplate<>(producerVarbindFactory());
    }

    @Bean
    public KStream<String, String> process(StreamsBuilder builder) {

        KStream<String, String> stream = builder.stream("usage-topic", Consumed.with(Serdes.String(), Serdes.String()));

        KStream<String, String> filtered = stream.filter((key, value) -> {

                    try {

                        CustomerUsage usage = objectMapper.readValue(value, CustomerUsage.class);
                        log.info("Customer : " + usage.getCustomerId());
                        log.info("Usage : " + usage.getUsage());
                        log.info("Timestamp : " + usage.getTimestamp());

                        if (usage.getUsage() > 1000) {
                            log.info("RECOMMENDATION GENERATED");
                        }

                        Timestamp threeMonthsAgo = Timestamp.valueOf(LocalDateTime.now().minusMonths(3));


                        log.info("Current Record Time : " + usage.getTimestamp());
                        log.info("Three Months Ago    : " + threeMonthsAgo);

                        boolean result = usage.getUsage() != null && usage.getUsage() > 900 && usage.getTimestamp() != null && usage.getTimestamp().after(threeMonthsAgo);

                        log.info("Filter Result : " + result);

                        return result;
                    } catch (Exception e) {

                        e.printStackTrace();
                        return false;
                    }
                });

        filtered.peek((key, value) -> {

            try {

                CustomerUsage usage = objectMapper.readValue(value, CustomerUsage.class);
                log.info("Customer : " + usage.getCustomerId());
                log.info("Usage : " + usage.getUsage());
                log.info("Timestamp : " + usage.getTimestamp());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        KStream<String, String> recommendationStream = filtered.mapValues(value -> {

                    try {

                        CustomerUsage usage = objectMapper.readValue(value, CustomerUsage.class);

                        RecommendationDTO recommendation = planService.getRecommendation(usage);

                        log.info("Mail is going to send");

                        try {
                            emailService.sendRecommendationMail(recommendation);
                            log.info("Mail sent ");
                        } catch (Exception e) {
                            log.error("Email failed", e);

                        }
                        

                        if (recommendation != null) {

                            return objectMapper.writeValueAsString(recommendation);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                });

        recommendationStream.filter((k, v) -> v != null).peek((k,v) -> System.out.println("SENDING TO recommendation-topic => " + v)).to("recommendation-topic");

        return filtered;
    }
}