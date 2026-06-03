package com.hcl.Troy.Service;

import com.fasterxml.jackson.databind.Module;
import com.hcl.Troy.DTO.AlarmEvent;
import com.hcl.Troy.DTO.AlertEvent;
import com.hcl.Troy.DTO.MonitoringEvent;
import com.hcl.Troy.DTO.SnmpTrapDTO;
import com.hcl.Troy.DTO.TrapVarbindDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlarmProducerService {

    private final KafkaTemplate<String, MonitoringEvent> metricsKafkaTemplate;

    private final KafkaTemplate<String, AlertEvent> alertKafkaTemplate;

    private final KafkaTemplate<String, SnmpTrapDTO> kafkaTrapTemplate;

    private final KafkaTemplate<String, TrapVarbindDTO> kafkaVarbindTrapTemplate;

    public AlarmProducerService(
            KafkaTemplate<String, MonitoringEvent> metricsKafkaTemplate, KafkaTemplate<String, AlertEvent> alertKafkaTemplate, KafkaTemplate<String, SnmpTrapDTO> kafkaTrapTemplate, KafkaTemplate<String, TrapVarbindDTO> kafkaVarbindTrapTemplate) {

        this.metricsKafkaTemplate = metricsKafkaTemplate;
        this.alertKafkaTemplate = alertKafkaTemplate;
        this.kafkaTrapTemplate = kafkaTrapTemplate;
        this.kafkaVarbindTrapTemplate = kafkaVarbindTrapTemplate;
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

    public void publishSnmoTrap(SnmpTrapDTO dto) {

        kafkaTrapTemplate.send(
                "snm-trap-topic",
                String.valueOf(dto.getTrapId()),
                dto);
    }


    public void publishTrapVarTrap(TrapVarbindDTO dto) {

        kafkaVarbindTrapTemplate.send(
                "trap-varbinds-topic",
                String.valueOf(dto.getOid()),
                dto);
    }




}