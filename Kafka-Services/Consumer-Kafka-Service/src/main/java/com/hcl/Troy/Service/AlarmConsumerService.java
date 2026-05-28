package com.hcl.Troy.Service;

import com.hcl.Troy.DTO.AlarmEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlarmConsumerService {

    private final List<AlarmEvent> consumedEvents =
            new ArrayList<>();

    @KafkaListener(
            topics = "alarm-events-new",
            groupId = "alarm-group"
    )
    public void consume(AlarmEvent event) {
        consumedEvents.add(event);
        System.out.println("Alarm Received : " + event);
    }

    public List<AlarmEvent> getAllEvents() {

        return consumedEvents;
    }
}