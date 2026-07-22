package com.hcl.Troy.Service;

import com.hcl.Troy.DTO.*;
import jakarta.annotation.PostConstruct;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlarmConsumerService {

    private final List<MonitoringEvent> consumedMetricsEvents = new ArrayList<>();

    private final List<AlertEvent> consumedAlertEvents = new ArrayList<>();

    private final List<SnmpTrapDTO> consumedSnmpTraps = new ArrayList<>();

    private final List<TrapVarbindDTO> consumedTrapVarbinds = new ArrayList<>();

    private final List<RecommendationDTO> recommendations = new ArrayList<>();

    @KafkaListener(topics = "metrics-topic", groupId = "metrics-group")
    public void consume(MonitoringEvent event) {
        consumedMetricsEvents.add(event);
        System.out.println("Metrics  Received : " + event);
    }

    @KafkaListener(topics = "alerts-topic", groupId = "metrics-group")
    public void consumeAlerts(AlertEvent event) {
        consumedAlertEvents.add(event);
        System.out.println("Alerts  Received : " + event);
    }



    @KafkaListener(topics = "snm-trap-topic", groupId = "metrics-group")
    public void consumeSnmpTraps(SnmpTrapDTO event) {
        consumedSnmpTraps.add(event);
        System.out.println("Snmp_traps  Received : " + event);
    }

    @KafkaListener(topics = "trap-varbinds-topic", groupId = "metrics-group")
    public void consumeSnmpTraps(TrapVarbindDTO event) {
        consumedTrapVarbinds.add(event);
        System.out.println("Trap_varbinds  Received : " + event);
    }


    public List<MonitoringEvent> getAllEvents() {

        return consumedMetricsEvents;
    }

    public List<AlertEvent> getAllAlertEvents() {

        return consumedAlertEvents;
    }


    @KafkaListener(topics = "recommendation-topic", groupId = "recommendation-group-v21", containerFactory = "kafkaListenerContainerRecommendationFactory")
    public void consume(RecommendationDTO dto) {

        recommendations.add(dto);

        System.out.println("SIZE = " + recommendations.size());

        System.out.println(dto);
    }


    public List<RecommendationDTO> getRecommendations(){
        return recommendations;
    }

    @PostConstruct
    public void init() {
        System.out.println("LIST SIZE AT STARTUP = " + recommendations.size());
    }

}