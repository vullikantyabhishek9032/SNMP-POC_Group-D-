package com.hcl.Troy.Service;

import com.fasterxml.jackson.databind.Module;
import com.hcl.Troy.DTO.AlarmEvent;
import com.hcl.Troy.DTO.AlertEvent;
import com.hcl.Troy.DTO.MonitoringEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlarmProducerService {

    private final KafkaTemplate<String, MonitoringEvent> metricsKafkaTemplate;

    private final KafkaTemplate<String, AlertEvent> alertKafkaTemplate;

    public AlarmProducerService(
            KafkaTemplate<String, MonitoringEvent> metricsKafkaTemplate,KafkaTemplate<String, AlertEvent> alertKafkaTemplate) {

        this.metricsKafkaTemplate = metricsKafkaTemplate;
        this.alertKafkaTemplate=alertKafkaTemplate;
    }



    public void publishMetrics(
            MonitoringEvent event) {

        metricsKafkaTemplate.send(
                "metrics-topic",
                event);
    }

    public void publishAlert(
            AlertEvent event) {

        alertKafkaTemplate.send(
                "alerts-topic",
                event);
    }
}