package com.hcl.Troy.Service;

import com.hcl.Troy.DTO.AlarmEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlarmProducerService {

    private final KafkaTemplate<String, AlarmEvent> kafkaTemplate;

    public AlarmProducerService(
            KafkaTemplate<String, AlarmEvent> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAlarm(AlarmEvent event) {

        kafkaTemplate.send("alarm-events-new", event);

        System.out.println("Alarm Sent");
    }

    public void publishCritical(AlarmEvent event) {

        event.setSeverity("CRITICAL");

        kafkaTemplate.send("critical-alarms", event);

        System.out.println("Critical Alarm Published");
    }
}