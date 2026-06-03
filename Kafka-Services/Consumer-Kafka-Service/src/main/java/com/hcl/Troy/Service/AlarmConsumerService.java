package com.hcl.Troy.Service;

import com.hcl.Troy.DTO.AlarmEvent;
import com.hcl.Troy.DTO.AlertEvent;
import com.hcl.Troy.DTO.MonitoringEvent;
import com.hcl.Troy.DTO.SnmpTrapDTO;
import com.hcl.Troy.DTO.TrapVarbindDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlarmConsumerService {

    private final List<MonitoringEvent> consumedMetricsEvents =
            new ArrayList<>();
    private final List<AlertEvent> consumedAlertEvents =
            new ArrayList<>();
    private final List<SnmpTrapDTO> consumedSnmpTraps =
            new ArrayList<>();
    private final List<TrapVarbindDTO> consumedTrapVarbinds =
            new ArrayList<>();


    @KafkaListener(
            topics = "metrics-topic",
            groupId = "metrics-group"
    )
    public void consume(MonitoringEvent event) {
        consumedMetricsEvents.add(event);
        System.out.println("Metrics  Received : " + event);
    }

    @KafkaListener(
            topics = "alerts-topic",
            groupId = "metrics-group"
    )
    public void consumeAlerts(AlertEvent event) {
        consumedAlertEvents.add(event);
        System.out.println("Alerts  Received : " + event);
    }



    @KafkaListener(
            topics = "snm-trap-topic",
            groupId = "metrics-group"
    )
    public void consumeSnmpTraps(SnmpTrapDTO event) {
        consumedSnmpTraps.add(event);
        System.out.println("Snmp_traps  Received : " + event);
    }

    @KafkaListener(
            topics = "trap-varbinds-topic",
            groupId = "metrics-group"
    )
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
}